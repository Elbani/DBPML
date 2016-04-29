package com.aca.dbpmla.traces;

import com.aca.dbpmla.entity.Trace;

import java.io.InputStream;
import java.util.List;

/**
 * Created by elban on 4/23/16.
 */
public interface TraceManager {

    List<Trace> readTrace(InputStream inputStream);

}
