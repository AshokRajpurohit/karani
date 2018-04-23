/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.tridip;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class HackerRank {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        HackerRank a = new HackerRank();
        MovieTitles.getMovieTitles(in.read());
        in.close();
        out.close();
    }

    private void solve() throws IOException {
        while (true) {
            out.println(in.read());
            out.flush();
        }
    }

    public static String longestEvenWord(String sentence) {
        String[] words = sentence.split(" ");
        return Arrays.stream(words)
                .filter((a) -> (a.length() & 1) == 0)
                .max((a, b) -> a.length() - b.length())
                .orElse("00");
    }

    final static class MovieTitles {
        private static final String HTTP_URL = "https://jsonmock.hackerrank.com/api/movies/search/?Title=", PAGE = "&page=";
        private static String[] getMovieTitles(String title) throws IOException {
            int totalPages = totalPages(title);
            String[] ar = new String[totalPages];
            int index = 0;
            for (int i = 1; i <= totalPages; i++) {
                ResponseModel model = toModel(getConnection(title, i));
                for (Movie movie: model.data) {
                    ar[index++] = movie.Title;
                }
            }

            Arrays.sort(ar);
            return ar;
        }

        private static int totalPages(String title) throws IOException {
            HttpURLConnection connection = getConnection(title, -1);
            return toModel(connection).total_pages;
        }

        private static ResponseModel toModel(HttpURLConnection connection) throws IOException {
            Gson gson = new GsonBuilder().create();
            JsonParser parser = new JsonParser();
            InputStream inputStream = connection.getInputStream();
            ResponseModel model = gson.fromJson(new InputStreamReader(inputStream), ResponseModel.class);
            return model;
        }

        private static HttpURLConnection getConnection(String title, int pageNumber) throws IOException {
            String urlString = HTTP_URL + title;
            if (pageNumber != 0) urlString = urlString + PAGE + pageNumber;
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            return connection;
        }

    }

    final static class Movie {
        String Poster, Title, Type, Year, imdbID;
    }

    final static class ResponseModel {
        int page, per_page, total, total_pages;
        Movie[] data;
    }
}
