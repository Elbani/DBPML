package com.aca.dbpmla.tracesImpl;

import com.aca.dbpmla.entity.Trace;
import com.aca.dbpmla.traces.TraceManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by elban on 4/23/16.
 */
public class TraceManagerImpl implements TraceManager {
    @Override
    public List<Trace> readTrace(InputStream inputStream) {

        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
        List<Trace> traces = new ArrayList<>();
        String line = null;

        while (true) {
            try {
                line = r.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (line == null) {
                break;
            }
            String [] tokens = line.split("\\s+");
            Trace trace = new Trace();
            trace.setInstructionAddress(Long.parseLong(tokens[1], 16));
            trace.setTakenNotBranch(tokens[6]);

            if(!trace.getTakenNotBranch().equals("-"))
                traces.add(trace);

        }
        return traces;
    }
}
