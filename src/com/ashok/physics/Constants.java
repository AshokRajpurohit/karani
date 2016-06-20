package com.ashok.physics;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public enum Constants {
    GravitationalConstant(6.67384e-11), PlanckConstant(6.62606957e-34);
    public final double value;

    private Constants(double value) {
        this.value = value;
    }
}
