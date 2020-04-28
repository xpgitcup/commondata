/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.edu.cup.physics;

/**
 *
 * @author LiXiaoping
 */
public interface CommonConst {

    /**
     * gravity（重力加速度）,m/s2
     */
    double GRAVITY = 9.8;

    /**
     * velocity of light（光速）,m/s
     */
    double VELOCITY_LIGHT = 299792458.0;

    /**
     * standard pressure（标准压力）,Pa
     */
    double STANDARD_PRESSURE = 101325.0;

    /**
     * default standard temperature（默认标准温度）,K
     */
    double STANDARD_TEMPERATURE = 273.15,
            STANDARD_TEMPERATURE_4 = STANDARD_TEMPERATURE + 4,
            STANDARD_TEMPERATURE_20 = STANDARD_TEMPERATURE + 20,
            STANDARD_TEMPERATURE_25 = STANDARD_TEMPERATURE + 25;

    /**
     * density of air@0C、20C（空气0、20℃密度）, 1atm,kg/m3
     */
    double DENSITY_AIR_STD_0 = 1.293, DENSITY_AIR_STD_20 = 1.206;
    
    /**
     * density of water@4C（水4℃密度）, 1atm,kg/m3
     */
    double DENSITY_STD_4_WATER = 1000.0;
    
    /**
     * molcecular mass of air（空气摩尔质量）,kg/kmol
     */
    double MOLEWEIGHT_AIR = 28.8;
    
    /**
     * Gas Constant,气体常数,J/mol·K
     */
    double R = 8.314472;
    
    /**
     * （默认临界雷诺数）
     */
    double REYNOLDSCRITICAL = 2000;
    
    /**
     * Avogadro Constant,阿伏伽德罗常量,1/mol
     */
    double NAV = 6.02214129e23;
    
    /**
     * Boltzmann Constant,玻尔兹曼常数, J/K
     */
    double BOLTZMANN = 1.3806488e-23;
    

    /**
     * Stefan-Boltzmann constant，斯蒂芬—玻尔兹曼常数，W/(m^2·K^4）
     */
    double STEFAN_BOLTZMANN = 5.67e-8;

}
