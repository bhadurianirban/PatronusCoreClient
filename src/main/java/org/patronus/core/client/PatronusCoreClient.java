/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.patronus.core.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.patronus.constants.PatronusServicePaths;
import org.patronus.core.dto.FractalDTO;
import org.patronus.response.FractalResponseCode;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.hedwig.cloud.dto.HedwigConstants;

public class PatronusCoreClient {

    private WebTarget webTarget;
    private Client client;
    private String BASE_URI;

    public PatronusCoreClient() {
        BASE_URI =  HedwigConstants.createConnectionUrl(PatronusServicePaths.PATRONUS_SERVER,PatronusServicePaths.PATRONUS_PORT,PatronusServicePaths.PATRONUS_BASE_URI);
    }
    public PatronusCoreClient(String server,String serverPort) {
        BASE_URI =  HedwigConstants.createConnectionUrl(server,serverPort,PatronusServicePaths.PATRONUS_BASE_URI);
    }
    
    private FractalDTO callFractalService(FractalDTO fractalDTO) {
        WebTarget resource = webTarget;
        ObjectMapper objectMapper = new ObjectMapper();
        String ipsvgcalcDTOJSON;
        try {
            ipsvgcalcDTOJSON = objectMapper.writeValueAsString(fractalDTO);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(PatronusCoreClient.class.getName()).log(Level.SEVERE, null, ex);
            fractalDTO.setResponseCode(FractalResponseCode.JSON_FORMAT_PROBLEM);
            return fractalDTO;
        }
        Invocation.Builder ib = resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON);
        Response response = ib.post(javax.ws.rs.client.Entity.entity(ipsvgcalcDTOJSON, javax.ws.rs.core.MediaType.APPLICATION_JSON));
//        Map<String, Object> ipsvgCalcResults = new HashMap<>();
        if (response.getStatus() != 200) {
            Logger.getLogger(PatronusCoreClient.class.getName()).log(Level.SEVERE, "Service connection response" + Integer.toString(response.getStatus()));
            fractalDTO.setResponseCode(FractalResponseCode.SERVICE_CONNECTION_FAILURE);
            return fractalDTO;
        }
        String respMapJSON = response.readEntity(String.class);
        try {
            fractalDTO = objectMapper.readValue(respMapJSON, FractalDTO.class);
        } catch (IOException ex) {
            Logger.getLogger(PatronusCoreClient.class.getName()).log(Level.SEVERE, null, ex);
            fractalDTO.setResponseCode(FractalResponseCode.JSON_FORMAT_PROBLEM);
            return fractalDTO;
        }
        return fractalDTO;
    }

    public FractalDTO calculateImprovedPSVG(FractalDTO fractalDTO) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(PatronusServicePaths.IMPROVED_PSVG_BASE + "/" + PatronusServicePaths.CALCULATE_IMPROVED_PSVG);
        return callFractalService(fractalDTO);
    }

    public FractalDTO queueImprovedPSVGCalculation(FractalDTO fractalDTO) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(PatronusServicePaths.IMPROVED_PSVG_BASE + "/" + PatronusServicePaths.QUEUE_IMPROVED_PSVG);
        return callFractalService(fractalDTO);
    }

    public FractalDTO deleteIPSVGResults(FractalDTO fractalDTO) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(PatronusServicePaths.IMPROVED_PSVG_BASE + "/" + PatronusServicePaths.DELETE_IMPROVED_PSVG);
        return callFractalService(fractalDTO);
    }

    public FractalDTO getIpsvgResults(FractalDTO fractalDTO) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(PatronusServicePaths.IMPROVED_PSVG_BASE + "/" + PatronusServicePaths.GET_IMPROVED_PSVG_RESULTS);
        return callFractalService(fractalDTO);
    }

    public FractalDTO calculateMFDFA(FractalDTO fractalDTO) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(PatronusServicePaths.MFDFA_BASE + "/" + PatronusServicePaths.CALCULATE_MFDFA);
        return callFractalService(fractalDTO);
    }

    public FractalDTO queueMFDFACalculation(FractalDTO fractalDTO) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(PatronusServicePaths.MFDFA_BASE + "/" + PatronusServicePaths.QUEUE_MFDFA_RESULTS);
        return callFractalService(fractalDTO);
    }

    public FractalDTO getMfdfaResults(FractalDTO fractalDTO) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(PatronusServicePaths.MFDFA_BASE + "/" + PatronusServicePaths.GET_MFDFA_RESULTS);
        return callFractalService(fractalDTO);
    }
    
    public FractalDTO getDfaResults(FractalDTO fractalDTO) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(PatronusServicePaths.MFDFA_BASE + "/" + PatronusServicePaths.GET_DFA_RESULTS);
        return callFractalService(fractalDTO);
    }

    public FractalDTO deleteMFDFAResults(FractalDTO fractalDTO) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(PatronusServicePaths.MFDFA_BASE + "/" + PatronusServicePaths.DELETE_MFDFA_RESULTS);
        return callFractalService(fractalDTO);
    }

    public FractalDTO calculateMFDXA(FractalDTO fractalDTO) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(PatronusServicePaths.MFDXA_BASE + "/" + PatronusServicePaths.CALCULATE_MFDXA);
        return callFractalService(fractalDTO);
    }

    public FractalDTO queueMFDXACalculation(FractalDTO fractalDTO) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(PatronusServicePaths.MFDXA_BASE + "/" + PatronusServicePaths.QUEUE_MFDXA_RESULTS);
        return callFractalService(fractalDTO);
    }

    public FractalDTO calculatePSVG(FractalDTO fractalDTO) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(PatronusServicePaths.PSVG_BASE + "/" + PatronusServicePaths.CALCULATE_PSVG);
        return callFractalService(fractalDTO);
    }

    public FractalDTO getPsvgResults(FractalDTO fractalDTO) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(PatronusServicePaths.PSVG_BASE + "/" + PatronusServicePaths.GET_PSVG_RESULTS);
        return callFractalService(fractalDTO);
    }

    public FractalDTO deletePSVGResults(FractalDTO fractalDTO) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(PatronusServicePaths.PSVG_BASE + "/" + PatronusServicePaths.DELETE_PSVG_RESULTS);
        return callFractalService(fractalDTO);
    }

    public FractalDTO queuePSVGCalculation(FractalDTO fractalDTO) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(PatronusServicePaths.PSVG_BASE + "/" + PatronusServicePaths.QUEUE_PSVG_RESULTS);
        return callFractalService(fractalDTO);
    }

    public FractalDTO deleteDataseries(FractalDTO fractalDTO) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(PatronusServicePaths.DATA_SERIES_BASE + "/" + PatronusServicePaths.DELETE_DATA_SERIES);
        return callFractalService(fractalDTO);
    }

    public FractalDTO getDataseries(FractalDTO fractalDTO) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(PatronusServicePaths.DATA_SERIES_BASE + "/" + PatronusServicePaths.GET_DATA_SERIES);
        return callFractalService(fractalDTO);
    }

    public FractalDTO createDataSeriesTermInstance(FractalDTO fractalDTO) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(PatronusServicePaths.DATA_SERIES_BASE + "/" + PatronusServicePaths.CREATE_DATA_SERIES_CMS_INSTANCE);
        return callFractalService(fractalDTO);
    }

    public FractalDTO importPSVGGraph(FractalDTO fractalDTO) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(PatronusServicePaths.GRAPH_BASE + "/" + PatronusServicePaths.GRAPH_IMPORT_FROM_VG);
        return callFractalService(fractalDTO);
    }

    public FractalDTO deleteGraph(FractalDTO fractalDTO) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(PatronusServicePaths.GRAPH_BASE + "/" + PatronusServicePaths.GRAPH_DELETE);
        return callFractalService(fractalDTO);
    }

    public FractalDTO calculateNetworkStats(FractalDTO fractalDTO) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(PatronusServicePaths.GRAPH_BASE + "/" + PatronusServicePaths.NETWORK_STATS_CALCULATION);
        return callFractalService(fractalDTO);
    }

    public FractalDTO deleteNetworkStats(FractalDTO fractalDTO) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(PatronusServicePaths.GRAPH_BASE + "/" + PatronusServicePaths.NETWORK_STATS_DELETE);
        return callFractalService(fractalDTO);
    }

    public FractalDTO uploadDataSeries(FractalDTO fractalDTO) {
        client = ClientBuilder.newBuilder().register(MultiPartFeature.class).build();
        webTarget = client.target(BASE_URI).path(PatronusServicePaths.DATA_SERIES_BASE + "/" + PatronusServicePaths.UPLOAD_DATA_SERIES);
        String csvFilePath = fractalDTO.getCsvFilePath();
        WebTarget resource = webTarget;
        ObjectMapper objectMapper = new ObjectMapper();
        String dataseriesUploadDTOJSON = null;
        try {
            dataseriesUploadDTOJSON = objectMapper.writeValueAsString(fractalDTO);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(PatronusCoreClient.class.getName()).log(Level.SEVERE, null, ex);
            fractalDTO.setResponseCode(FractalResponseCode.DATA_SERIES_FORMAT_PROBLEM);
            return fractalDTO;
        }
//        FileDataBodyPart filePart = new FileDataBodyPart("file", new File("/home/dgrfiv/Downloads/2018-07-09/V1/ch201.csv"));
        FileDataBodyPart filePart = new FileDataBodyPart("file", new File(csvFilePath));
        MultiPart multipartEntity = new FormDataMultiPart()
                .field("uploadDTO", dataseriesUploadDTOJSON)
                .bodyPart(filePart);
        Response response = resource.request().post(Entity.entity(multipartEntity, multipartEntity.getMediaType()));
        if (response.getStatus() != 200) {
            Logger.getLogger(PatronusCoreClient.class.getName()).log(Level.SEVERE, "Service connection response" + Integer.toString(response.getStatus()));
            fractalDTO.setResponseCode(FractalResponseCode.SERVICE_CONNECTION_FAILURE);
            return fractalDTO;
        }
        String respMapJSON = response.readEntity(String.class);
        try {
            fractalDTO = objectMapper.readValue(respMapJSON, FractalDTO.class);
        } catch (IOException ex) {
            Logger.getLogger(PatronusCoreClient.class.getName()).log(Level.SEVERE, null, ex);
            fractalDTO.setResponseCode(FractalResponseCode.DATA_SERIES_FORMAT_PROBLEM);
            return fractalDTO;
        }
        response.close();
        return fractalDTO;
    }
    public FractalDTO uploadGraph(FractalDTO fractalDTO) {
        client = ClientBuilder.newBuilder().register(MultiPartFeature.class).build();
        webTarget = client.target(BASE_URI).path(PatronusServicePaths.GRAPH_BASE + "/" + PatronusServicePaths.GRAPH_UPLOAD);
        String csvFilePath = fractalDTO.getCsvFilePath();
        WebTarget resource = webTarget;
        ObjectMapper objectMapper = new ObjectMapper();
        String dataseriesUploadDTOJSON = null;
        try {
            dataseriesUploadDTOJSON = objectMapper.writeValueAsString(fractalDTO);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(PatronusCoreClient.class.getName()).log(Level.SEVERE, null, ex);
            fractalDTO.setResponseCode(FractalResponseCode.DATA_SERIES_FORMAT_PROBLEM);
            return fractalDTO;
        }
//        FileDataBodyPart filePart = new FileDataBodyPart("file", new File("/home/dgrfiv/Downloads/2018-07-09/V1/ch201.csv"));
        FileDataBodyPart filePart = new FileDataBodyPart("file", new File(csvFilePath));
        MultiPart multipartEntity = new FormDataMultiPart()
                .field("uploadDTO", dataseriesUploadDTOJSON)
                .bodyPart(filePart);
        Response response = resource.request().post(Entity.entity(multipartEntity, multipartEntity.getMediaType()));
        if (response.getStatus() != 200) {
            Logger.getLogger(PatronusCoreClient.class.getName()).log(Level.SEVERE, "Service connection response" + Integer.toString(response.getStatus()));
            fractalDTO.setResponseCode(FractalResponseCode.SERVICE_CONNECTION_FAILURE);
            return fractalDTO;
        }
        String respMapJSON = response.readEntity(String.class);
        try {
            fractalDTO = objectMapper.readValue(respMapJSON, FractalDTO.class);
        } catch (IOException ex) {
            Logger.getLogger(PatronusCoreClient.class.getName()).log(Level.SEVERE, null, ex);
            fractalDTO.setResponseCode(FractalResponseCode.DATA_SERIES_FORMAT_PROBLEM);
            return fractalDTO;
        }
        response.close();
        return fractalDTO;
    }
}
