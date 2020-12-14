/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.patronus.core.client;

import java.io.IOException;
import org.patronus.core.dto.FractalDTO;
import org.hedwig.cloud.dto.HedwigAuthCredentials;

/**
 *
 * @author dgrfiv
 */
public class MapClientMain {

    public static void main(String[] args) throws IOException {

        //getFractal();
        //uploadDataSeries();
        importGraph();
    }

    public static void importGraph() {
        
        
        HedwigAuthCredentials authCredentials = new HedwigAuthCredentials();
        authCredentials.setUserId("bhaduri");
        authCredentials.setPassword("1234");
        authCredentials.setTenantId(1);
        authCredentials.setProductId(3);
        FractalDTO fractalDTO = new FractalDTO();
        fractalDTO.setHedwigAuthCredentials(authCredentials);
        //fractalDTO.setImportFromVGInstanceSlug("bpsvgHorizontal");
        

        PatronusCoreClient graphClient = new PatronusCoreClient();
        fractalDTO = graphClient.importPSVGGraph(fractalDTO);
        System.out.println(fractalDTO.getResponseCode());
    }
    public static void uploadDataSeries() {
        
        HedwigAuthCredentials authCredentials = new HedwigAuthCredentials();
        authCredentials.setUserId("bhaduri");
        authCredentials.setPassword("1234");
        authCredentials.setTenantId(1);
        authCredentials.setProductId(3);
        FractalDTO fractalDTO = new FractalDTO();
        fractalDTO.setHedwigAuthCredentials(authCredentials);
        fractalDTO.setParamSlug("dataseries");
        PatronusCoreClient dsuc = new PatronusCoreClient();
        dsuc.uploadDataSeries(fractalDTO);

    }

    public static void getFractal() {
        
        
        HedwigAuthCredentials authCredentials = new HedwigAuthCredentials();
        authCredentials.setUserId("bhaduri");
        authCredentials.setPassword("1234");
        authCredentials.setTenantId(1);
        authCredentials.setProductId(3);
        FractalDTO fractalDTO = new FractalDTO();
        fractalDTO.setHedwigAuthCredentials(authCredentials);
        fractalDTO.setDataSeriesSlug("c");
        fractalDTO.setParamSlug("default");
        fractalDTO.setCalcType("Normal");

        PatronusCoreClient ipsvgcalcClient = new PatronusCoreClient();
        fractalDTO = ipsvgcalcClient.calculateImprovedPSVG(fractalDTO);
    }
}