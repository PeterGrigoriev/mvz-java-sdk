package com.movilizer.masterdata;

import com.movilitas.movilizer.v12.MovilizerMasterdataDelete;
import com.movilitas.movilizer.v12.MovilizerMasterdataPoolUpdate;
import com.movilitas.movilizer.v12.MovilizerMasterdataReference;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MasterdataXmlReaderTest {
    private static final String XML_WITH_DIFFERENT_METERS = "<?xml version=\"1.0\"?>\n" +
            "<METERS>\n" +
            " <METER>\n" +
            "  <EVENT_ID>454375</EVENT_ID>\n" +
            "  <EVENT_TYPE>C</EVENT_TYPE>\n" +
            "  <INS_CD>9892</INS_CD>\n" +
            "  <NUM_CPT>8830</NUM_CPT>\n" +
            "  <NOM_CPT>S/ST B11</NOM_CPT>\n" +
            "  <DT_DEBUT>01/01/60</DT_DEBUT>\n" +
            "  <FL_TOP_P1>N</FL_TOP_P1>\n" +
            "  <UNT_SYMBOLE>MWh</UNT_SYMBOLE>\n" +
            "  <NRJ_CD>CHA</NRJ_CD>\n" +
            "  <TELE_RELEVE>0</TELE_RELEVE>\n" +
            "  <DT_SAISIE>28/06/13</DT_SAISIE>\n" +
            "  <ETAT_RLV>REL</ETAT_RLV>\n" +
            "  <MOMENT>MAT</MOMENT>\n" +
            "  <NUM_ORD>3000</NUM_ORD>\n" +
            "  <VAL_MAX>99999</VAL_MAX>\n" +
            "  <VIRGULE>0</VIRGULE>\n" +
            "  <STATUT_RLV>OK</STATUT_RLV>\n" +
            "  <IDX>13706</IDX>\n" +
            "  <CONSO>3</CONSO>\n" +
            "  <FL_RLVPCT>N</FL_RLVPCT>\n" +
            "  <FL_STK>0</FL_STK>\n" +
            "  <CONSO_ESTIMEE>1</CONSO_ESTIMEE>\n" +
            " </METER>\n" +
            " <METER>\n" +
            "  <EVENT_ID>454376</EVENT_ID>\n" +
            "  <EVENT_TYPE>C</EVENT_TYPE>\n" +
            "  <INS_CD>10337</INS_CD>\n" +
            "  <NUM_CPT>15401</NUM_CPT>\n" +
            "  <NOM_CPT>COMPTEUR GAZ DE REMPLACEMENT</NOM_CPT>\n" +
            "  <DT_DEBUT>01/03/01</DT_DEBUT>\n" +
            "  <FL_TOP_P1>0</FL_TOP_P1>\n" +
            "  <UNT_SYMBOLE>m3</UNT_SYMBOLE>\n" +
            "  <NRJ_CD>NAT</NRJ_CD>\n" +
            "  <TELE_RELEVE>0</TELE_RELEVE>\n" +
            "  <DT_SAISIE>21/03/01</DT_SAISIE>\n" +
            "  <ETAT_RLV>REL</ETAT_RLV>\n" +
            "  <MOMENT>MAT</MOMENT>\n" +
            "  <NUM_ORD>2010</NUM_ORD>\n" +
            "  <VAL_MAX>999999</VAL_MAX>\n" +
            "  <STATUT_RLV>OK</STATUT_RLV>\n" +
            "  <IDX>2811</IDX>\n" +
            "  <CONSO>2811</CONSO>\n" +
            "  <FL_RLVPCT>N</FL_RLVPCT>\n" +
            "  <FL_STK>0</FL_STK>\n" +
            " </METER>\n" +
            " <METER>\n" +
            "  <EVENT_ID>454377</EVENT_ID>\n" +
            "  <EVENT_TYPE>D</EVENT_TYPE>\n" +
            "  <NUM_CPT>15402</NUM_CPT>\n" +
            " </METER>\n" +
            "</METERS>\n";

    private static final String XML_WITH_TWO_UPDATES = "<?xml version=\"1.0\"?>\n" +
            "<METERS>\n" +
            " <METER>\n" +
            "  <EVENT_ID>454375</EVENT_ID>\n" +
            "  <EVENT_TYPE>C</EVENT_TYPE>\n" +
            "  <INS_CD>9892</INS_CD>\n" +
            "  <NUM_CPT>8830</NUM_CPT>\n" +
            "  <NOM_CPT>S/ST B11</NOM_CPT>\n" +
            "  <DT_DEBUT>01/01/60</DT_DEBUT>\n" +
            "  <FL_TOP_P1>N</FL_TOP_P1>\n" +
            "  <UNT_SYMBOLE>MWh</UNT_SYMBOLE>\n" +
            "  <NRJ_CD>CHA</NRJ_CD>\n" +
            "  <TELE_RELEVE>0</TELE_RELEVE>\n" +
            "  <DT_SAISIE>28/06/13</DT_SAISIE>\n" +
            "  <ETAT_RLV>REL</ETAT_RLV>\n" +
            "  <MOMENT>MAT</MOMENT>\n" +
            "  <NUM_ORD>3000</NUM_ORD>\n" +
            "  <VAL_MAX>99999</VAL_MAX>\n" +
            "  <VIRGULE>0</VIRGULE>\n" +
            "  <STATUT_RLV>OK</STATUT_RLV>\n" +
            "  <IDX>13706</IDX>\n" +
            "  <CONSO>3</CONSO>\n" +
            "  <FL_RLVPCT>N</FL_RLVPCT>\n" +
            "  <FL_STK>0</FL_STK>\n" +
            "  <CONSO_ESTIMEE>1</CONSO_ESTIMEE>\n" +
            " </METER>\n" +
            " <METER>\n" +
            "  <EVENT_ID>454376</EVENT_ID>\n" +
            "  <EVENT_TYPE>C</EVENT_TYPE>\n" +
            "  <INS_CD>10337</INS_CD>\n" +
            "  <NUM_CPT>8830</NUM_CPT>\n" +
            "  <NOM_CPT>COMPTEUR GAZ DE REMPLACEMENT</NOM_CPT>\n" +
            "  <DT_DEBUT>01/03/01</DT_DEBUT>\n" +
            "  <FL_TOP_P1>0</FL_TOP_P1>\n" +
            "  <UNT_SYMBOLE>m3</UNT_SYMBOLE>\n" +
            "  <NRJ_CD>NAT</NRJ_CD>\n" +
            "  <TELE_RELEVE>0</TELE_RELEVE>\n" +
            "  <DT_SAISIE>21/03/01</DT_SAISIE>\n" +
            "  <ETAT_RLV>REL</ETAT_RLV>\n" +
            "  <MOMENT>MAT</MOMENT>\n" +
            "  <NUM_ORD>2010</NUM_ORD>\n" +
            "  <VAL_MAX>999999</VAL_MAX>\n" +
            "  <STATUT_RLV>OK</STATUT_RLV>\n" +
            "  <IDX>2811</IDX>\n" +
            "  <CONSO>2811</CONSO>\n" +
            "  <FL_RLVPCT>N</FL_RLVPCT>\n" +
            "  <FL_STK>0</FL_STK>\n" +
            " </METER>\n" +
            " <METER>\n" +
            "  <EVENT_ID>454377</EVENT_ID>\n" +
            "  <EVENT_TYPE>D</EVENT_TYPE>\n" +
            "  <NUM_CPT>15402</NUM_CPT>\n" +
            " </METER>\n" +
            "</METERS>\n";


    private IMasterdataXmlSetting settingsForMeters;
    private MasterdataXmlReader reader;

    public MasterdataXmlReaderTest() {
        MasterdataFieldNames fieldNames = new MasterdataFieldNames("INS_CD", "NUM_CPT", "NOM_CPT");
        settingsForMeters = new MasterdataXmlSettings("MeterApp", "METER", 500, 1, fieldNames, null);
    }

    @BeforeMethod
    public void setUp() throws Exception {
        reader = new MasterdataXmlReader();
    }

    @Test
    public void testRead() throws Exception {
        MovilizerMasterdataPoolUpdate update = reader.read(new StringReader(XML_WITH_DIFFERENT_METERS), settingsForMeters).getMasterdataPoolUpdate();

        assertEquals(update.getUpdate().size(), 2);
        assertEquals(update.getDelete().size(), 1);
    }


    @Test
    public void testReadWithTwoUpdates() throws Exception {
        MovilizerMasterdataPoolUpdate update = reader.read(new StringReader(XML_WITH_TWO_UPDATES), settingsForMeters).getMasterdataPoolUpdate();
        assertEquals(update.getUpdate().size(), 1);
        assertEquals(update.getDelete().size(), 1);
    }


    private static String SETTING_WITH_REFERENCE = "<xml>\n" +
            "                <subscriber>FRI</subscriber>\n" +
            "                <pool>routeMvl</pool>\n" +
            "                <target-pool>installation_eMove</target-pool>\n" +
            "                <limit>100</limit>\n" +
            "                <loops>1</loops>\n" +
            "                <fields>\n" +
            "                    <object-id>INS_CD</object-id>\n" +
            "                    <event-type>OBJECT_FUNCTION</event-type>\n" +
            "                    <group>PER_NUM</group>\n" +
            "                </fields>\n" +
            "            </xml>\n";

    private final static String XML_WITH_REFERENCE = "<?xml version=\"1.0\"?>\n" +
            "<ROWSET>\n" +
            " <ROW>\n" +
            "  <EVENT_ID>1172</EVENT_ID>\n" +
            "  <EVENT_STATUS>NEW</EVENT_STATUS>\n" +
            "  <OBJECT_KEY>853</OBJECT_KEY>\n" +
            "  <OBJECT_FUNCTION>Create</OBJECT_FUNCTION>\n" +
            "  <SUBSCRIBER>FRI</SUBSCRIBER>\n" +
            "  <OBJECT_TYPE>routeMvl</OBJECT_TYPE>\n" +
            "  <NUM>570</NUM>\n" +
            "  <PER_NUM>4067</PER_NUM>\n" +
            "  <INS_CD>12902</INS_CD>\n" +
            "  <NUM_ORD>1</NUM_ORD>\n" +
            "  <MIN_EVENT_ID>1172</MIN_EVENT_ID>\n" +
            " </ROW>\n" +
            "</ROWSET>\n";

    private final static String XML_WITH_DEREFERENCE = "<?xml version=\"1.0\"?>\n" +
            "<ROWSET>\n" +
            " <ROW>\n" +
            "  <EVENT_ID>1173</EVENT_ID>\n" +
            "  <EVENT_STATUS>NEW</EVENT_STATUS>\n" +
            "  <OBJECT_KEY>853</OBJECT_KEY>\n" +
            "  <OBJECT_FUNCTION>Delete</OBJECT_FUNCTION>\n" +
            "  <SUBSCRIBER>FRI</SUBSCRIBER>\n" +
            "  <OBJECT_TYPE>routeMvl</OBJECT_TYPE>\n" +
            "  <NUM>570</NUM>\n" +
            "  <PER_NUM>4067</PER_NUM>\n" +
            "  <INS_CD>12902</INS_CD>\n" +
            "  <NUM_ORD>1</NUM_ORD>\n" +
            "  <MIN_EVENT_ID>1173</MIN_EVENT_ID>\n" +
            " </ROW>\n" +
            "</ROWSET>\n";


    @Test
    public void testReadWithReference() throws ConfigurationException, IOException, XMLStreamException {
        IMasterdataXmlSetting settings = MasterdataXmlSettings.fromXml(SETTING_WITH_REFERENCE);


        IMasterdataReaderResult readerResult = reader.read(new StringReader(XML_WITH_REFERENCE), settings);
        MovilizerMasterdataPoolUpdate poolUpdate = readerResult.getMasterdataPoolUpdate();
        assertEquals(poolUpdate.getPool(), "installation_eMove");
        assertEquals(poolUpdate.getUpdate().size(), 0);
        assertEquals(poolUpdate.getDelete().size(), 0);
        List<MovilizerMasterdataReference> references = poolUpdate.getReference();
        assertEquals(references.size(), 1);
        MovilizerMasterdataReference reference = references.get(0);
        assertEquals(reference.getGroup(), "4067");
        assertEquals(reference.getKey(), "12902");
        assertTrue(reference.getMasterdataAckKey().contains("1172"));
    }

    @Test
    public void testReadWithDereference() throws ConfigurationException, IOException, XMLStreamException {
        IMasterdataXmlSetting settings = MasterdataXmlSettings.fromXml(SETTING_WITH_REFERENCE);


        IMasterdataReaderResult readerResult = reader.read(new StringReader(XML_WITH_DEREFERENCE), settings);
        MovilizerMasterdataPoolUpdate poolUpdate = readerResult.getMasterdataPoolUpdate();
        assertEquals(poolUpdate.getPool(), "installation_eMove");
        assertEquals(poolUpdate.getUpdate().size(), 0);
        assertEquals(poolUpdate.getReference().size(), 0);
        List<MovilizerMasterdataDelete> deletes = poolUpdate.getDelete();
        assertEquals(deletes.size(), 1);
        MovilizerMasterdataDelete delete = deletes.get(0);
        assertEquals(delete.getGroup(), "4067");
        assertEquals(delete.getKey(), "12902");
        assertTrue(delete.getMasterdataAckKey().contains("1173"));
    }
}
