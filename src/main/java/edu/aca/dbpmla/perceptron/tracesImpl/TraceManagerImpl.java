package edu.aca.dbpmla.perceptron.tracesImpl;

import edu.aca.dbpmla.perceptron.entity.Trace;
import edu.aca.dbpmla.perceptron.traces.TraceManager;

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
            trace.setInstructionAddress(Long.parseLong(tokens[0], 16));
            trace.setTakenNotBranch(tokens[1]);

            traces.add(trace);

        }
        return traces;
    }
}
