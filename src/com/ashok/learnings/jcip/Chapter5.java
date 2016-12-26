package com.ashok.learnings.jcip;

import com.ashok.lang.annotation.ThreadSafe;
import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import com.sun.istack.internal.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;

/**
 * Code snippets from Java Concurrency in Practice by Brian Goetz.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Chapter5 {

    private final static InputReader input = new InputReader();
    private final static Output out = new Output();

    /**
     * Listing 5.17
     */
    final static class RendererUsingFutureList {
        private static final int NTHREADS = 100;
        private ExecutorService executor = Executors.newScheduledThreadPool(NTHREADS);

        RendererUsingFutureList(ExecutorService executor) {
            this.executor = executor;
        }

        public List<ImageData> getRankedImageDatas(
                ImageInfo imageInfo, Set<ImageSource> source,
                Comparator<ImageData> ranking, long time,
                TimeUnit timeUnit) throws InterruptedException {
            List<ImageTask> tasks = new ArrayList<>();

            for (ImageSource imageSource : source) {
                tasks.add(new ImageTask(imageSource, imageInfo));
            }

            List<Future<ImageData>> futures = executor.invokeAll(tasks, time, timeUnit);
            List<ImageData> images = new ArrayList<>(tasks.size());

            Iterator<ImageTask> taskIterator = tasks.iterator();

            for (Future<ImageData> f : futures) {
                ImageTask task = taskIterator.next();

                try {
                    images.add(task.call());
                } catch (ExecutionException e) {
                    // add something to images to mention the failur cause.
                    images.add(task.getFailureTask(e.getCause()));
                } catch (CancellationException e) {
                    images.add(task.getFailureTask(e.getCause()));
                } catch (Exception e) {
                    images.add(task.getFailureTask(e.getCause()));
                    e.printStackTrace();
                }
            }

            Collections.sort(images, ranking);
            return images;
        }
    }

    /**
     * Listing 5.15
     */
    @ThreadSafe
    final static class Renderer {
        private final ExecutorService executor;

        Renderer(ExecutorService executor) {
            this.executor = executor;
        }

        void renderPage(CharSequence source) {
            final List<ImageInfo> info = FutureRenderer.scnaForImageInfo(source);
            CompletionService<ImageData> completionService =
                    new ExecutorCompletionService<ImageData>(executor);

            for (final ImageInfo imageInfo : info) {
                completionService.submit(new Callable<ImageData>() {
                    @Override
                    public ImageData call() throws Exception {
                        return imageInfo.downloadImage();
                    }
                });
            }

            // renderText(source);

            try {
                for (int t = 0, n = info.size(); t < n; t++) {
                    Future<ImageData> f = completionService.take();
                    ImageData imageData = f.get();
                    renderImage(imageData);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        void renderImage(ImageData imageData) {
            out.println(imageData.data);
        }
    }

    final static class ImageTask implements Callable<ImageData> {
        private final ImageSource source;
        private final ImageInfo imageInfo;

        public ImageTask(ImageSource source, ImageInfo info) {
            this.source = source;
            imageInfo = info;
        }

        @Override
        public ImageData call() throws Exception {
            return source.downloadImage(imageInfo);
        }

        public ImageData getFailureTask(Throwable cause) {
            return new ImageData(new ImageInfo());
        }
    }

    /**
     * Listing 5.13
     */
    @ThreadSafe
    final static class FutureRenderer {
        private static final int NTHREADS = 100;
        private final ExecutorService executor = Executors.newScheduledThreadPool(NTHREADS);

        void renderPage(CharSequence source) {
            List<ImageInfo> imageInfos = scnaForImageInfo(source);
            Callable<List<ImageData>> task =
                    new Callable<List<ImageData>>() {
                        @Override
                        public List<ImageData> call() throws Exception {
                            List<ImageData> result
                                    = new ArrayList<>();

                            for (ImageInfo info : imageInfos)
                                result.add(info.downloadImage());

                            return null;
                        }
                    };

            Future<List<ImageData>> future = executor.submit(task);
            renderPage(source);

            try {
                List<ImageData> imageData = future.get();

                for (ImageData data : imageData)
                    renderImage(data);

            } catch (InterruptedException e) {
                // Re-assert the thread's interrupted status
                Thread.currentThread().interrupt();
                // We don't need the result, so cancel the task too
                future.cancel(true);
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        private static void renderImage(ImageData image) {
            out.println(image.data);
        }

        private Object readResolve() throws ObjectStreamException {
            return new FutureRenderer();
        }

        private static List<ImageInfo> scnaForImageInfo(CharSequence source) {
            List<ImageInfo> res = new LinkedList<>();

            for (int i = 1; i < 10; i++) {
                ImageInfo info = new ImageInfo();
                info.v = i;
                res.add(info);
            }

            return res;
        }
    }

    final static class LifeCycleWebServer {
        private static final int NTHREADS = 100;
        private final ExecutorService exec = Executors.newScheduledThreadPool(NTHREADS);

        public void start() throws IOException {
            ServerSocket socket = new ServerSocket(80);
            while (!exec.isShutdown()) {
                try {
                    final Socket connection = socket.accept();
                    exec.execute(new Runnable() {
                        @Override
                        public void run() {
                            handleRequest(connection);
                        }
                    });
                } catch (RejectedExecutionException e) {
                    if (!exec.isShutdown())
                        out.println("task submission rejected", e);
                }
            }
        }

        public void stop() {
            exec.shutdown();
        }

        private void handleRequest(Socket connection) {
            try {
                InputStream inputStream = connection.getInputStream();
                InputReader inputReader = new InputReader(inputStream);
                boolean res = true;
                int req = inputReader.readInt();

                if (isShutdownRequest(req))
                    stop();
                else
                    dispatchRequest(req);
            } catch (IOException e) {
                out.println("socket input stream reading failed", e);
            }
        }

        private static void dispatchRequest(int req) {
            // do anything you want
        }

        private static boolean isShutdownRequest(int req) {
            return (req & 1) == 1;
        }
    }

    final static class TaskExecutionWebServer {
        private static final int NTHREADS = 100;
        private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);

        private static void run() throws IOException {
            ServerSocket socket = new ServerSocket(80);
            while (true) {
                final Socket connection = socket.accept();

                Runnable task = new Runnable() {
                    @Override
                    public void run() {
                        handleRequest(connection);
                    }
                };

                exec.execute(task);
            }
        }

        private static void handleRequest(@NotNull Socket connection) {
            // do anything you want with connection
        }
    }

    final static class HtmlServerTest {
        private static void run() throws IOException {
            ServerSocket socket = new ServerSocket(80);
            Socket connection = socket.accept();
            socket.getLocalSocketAddress();
        }
    }

    static class ImageSource {
        ImageData downloadImage(ImageInfo info) {
            return new ImageData(info);
        }
    }

    static class ImageInfo {
        int v = 10;
        String info;

        ImageInfo(String message) {
            info = message;
            v = message.length();
        }

        ImageInfo() {
            info = "Ashok";
            v = info.length();
        }

        public ImageData downloadImage() {
            return new ImageData(this);
        }
    }

    static class ImageData {
        int data = 0;

        ImageData(ImageInfo info) {
            data = (info.v << 5) | info.v;
        }
    }
}
