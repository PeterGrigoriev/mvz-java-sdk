package com.movilizer.moveletbuilder;

import com.movilitas.movilizer.v15.MovilizerMovelet;
import com.movilizer.jaxb.MovilizerJaxbUnmarshaller;
import com.movilizer.util.dbc.Ensure;
import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class XmlMoveletBuilder implements IMoveletBuilder {
    private static final ILogger logger = ComponentLogger.getInstance("Movilizer.XmlMoveletBuilder");
    private final String moveletXml;

    public XmlMoveletBuilder(String moveletXml) {
        Ensure.ensureNotNullOrEmpty(moveletXml, "moveletXml");
        this.moveletXml = moveletXml;
    }

    @Override
    public List<MovilizerMovelet> buildMovelets() {
        try {

            Class<MovilizerMovelet> declaredType = MovilizerMovelet.class;

            MovilizerJaxbUnmarshaller unmarshaller = MovilizerJaxbUnmarshaller.getInstance();
            MovilizerMovelet value = unmarshaller.unmarshall(declaredType, moveletXml);
            return singletonList(value);

        } catch (Throwable e) {
            logger.error(e);
            logger.error("Cannot de-serialize XmlMovelet");
            logger.error(moveletXml);
            return new ArrayList<MovilizerMovelet>();
        }
    }


    @Override
    public void onRequestAction(RequestEvent action) {
    }
}
