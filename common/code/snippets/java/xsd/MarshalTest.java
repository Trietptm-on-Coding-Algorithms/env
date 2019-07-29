package com.foo.jaxtest;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3._2000._09.xmldsig_.SignatureType;

import es.gob.administracionelectronica.eni.xsd.v1_0.documento_e.contenido.TipoContenido;
import es.gob.administracionelectronica.eni.xsd.v1_0.expediente_e.indice_e.ObjectFactory;
import es.gob.administracionelectronica.eni.xsd.v1_0.expediente_e.indice_e.TipoIndice;
import es.gob.administracionelectronica.eni.xsd.v1_0.expediente_e.indice_e.contenido.TipoDocumentoIndizado;
import es.gob.administracionelectronica.eni.xsd.v1_0.expediente_e.indice_e.contenido.TipoIndiceContenido;
import es.gob.administracionelectronica.eni.xsd.v1_0.firma.Firmas;
import es.gob.administracionelectronica.eni.xsd.v1_0.firma.TipoFirmasElectronicas;
import es.gob.administracionelectronica.eni.xsd.v1_0.firma.TipoFirmasElectronicas.ContenidoFirma;

@SuppressWarnings("restriction")
public class MarshalTest {
	private ObjectFactory objectfactory = new ObjectFactory();
	private JAXBElement<TipoIndice> tipoIndice;
	private SignatureType signature; 

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws JAXBException {        
        // Unmarshall Signature
		
		final String signaturePath = "src/main/resources/app/signatures/1.sig";
        File file = new File(signaturePath);
        JAXBContext jaxbContext = JAXBContext.newInstance(SignatureType.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        signature = ((JAXBElement<SignatureType>) unmarshaller.unmarshal(file)).getValue();
        
        // Marshall Indice
		
		TipoIndice instance = objectfactory.createTipoIndice();
		instance.setId("MOCK_ID_1");
		
		// IndiceContenido

		TipoIndiceContenido tipoIndiceContenido = new TipoIndiceContenido();
		GregorianCalendar gregorianDate = new GregorianCalendar();
		gregorianDate.setTime(new Date());
		XMLGregorianCalendar fechaDate;
		try {
			fechaDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianDate);
		} catch (DatatypeConfigurationException e) {
			throw new AssertionError(e);
		}
		TipoDocumentoIndizado tipoDocumentoIndizado = new TipoDocumentoIndizado();
		tipoDocumentoIndizado.setIdentificadorDocumento("MOCK_DOC_ID_1");
		tipoDocumentoIndizado.setValorHuella("fe1f647951885bf60e1510f19bafa5064306e5eb485aa68cf78b4f2ed781736e");
		tipoDocumentoIndizado.setFuncionResumen("http://www.w3.org/2001/04/xmlenc#sha256");
		
		tipoIndiceContenido.getDocumentoIndizadoOrExpedienteIndizadoOrCarpetaIndizada().add(tipoDocumentoIndizado);
		tipoIndiceContenido.setFechaIndiceElectronico(fechaDate);
		instance.setIndiceContenido(tipoIndiceContenido);

		// Firmas
		
		Firmas firmas = new Firmas();
		TipoFirmasElectronicas tipoFirmas = new TipoFirmasElectronicas();
		ContenidoFirma contenidoFirma = new ContenidoFirma();
		ContenidoFirma.FirmaConCertificado firmaConCertificado = new ContenidoFirma.FirmaConCertificado();
		//firmaConCertificado.setReferenciaFirma("#ID_CONT_1");
		firmaConCertificado.setSignature(signature);

		contenidoFirma.setFirmaConCertificado(firmaConCertificado);
		tipoFirmas.setContenidoFirma(contenidoFirma);
		tipoFirmas.setTipoFirma("TF02");
		firmas.getFirma().add(tipoFirmas);
		instance.setFirmas(firmas);

		tipoIndice = objectfactory.createIndice(instance);
	}

	@After
	public void tearDown() {
		tipoIndice = null;
	}

	public void shouldBeValid(String xsdPath, String xmlPath) {
		try {
			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = factory.newSchema(new File(xsdPath));
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(new File(xmlPath)));
		} catch (Exception e) {
			throw new AssertionError(e);
		}
	}

	@Test
	public void tipoIndice() throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance(TipoIndice.class);
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		final String xsdPath = "src/main/resources/app/xsd/eni/IndiceExpedienteEni.xsd";
		final String xmlPath = "tipoIndice.xml";
		marshaller.marshal(tipoIndice, new File(xmlPath));
		marshaller.marshal(tipoIndice, System.out);

		shouldBeValid(xsdPath, xmlPath);
	}
}
