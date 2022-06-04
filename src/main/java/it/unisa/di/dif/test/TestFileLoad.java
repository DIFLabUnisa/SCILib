package it.unisa.di.dif.test;

import it.unisa.di.dif.pattern.ReferencePattern;
import it.unisa.di.dif.pattern.ResidualNoise;

import java.io.File;
import java.io.IOException;

public class TestFileLoad {
    public static void main(String[] args) throws IOException {
        File f = new File("/Users/bruand/IdeaProjects/SCI_RP_Threshold_Java/results/rps/1/IC_190.rp");
        ReferencePattern rp = ReferencePattern.load(f);
        System.out.println(rp);
        f = new File("/Users/bruand/IdeaProjects/SCI_RP_Threshold_Java/results/rns/IC_191/i_51879.rn");
        ResidualNoise rn = ResidualNoise.load(f);
        System.out.println(rn);
    }
}
