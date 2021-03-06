/**
 * 
 */
package net.sf.testium.executor.webdriver.commands;

import java.io.File;
import java.util.ArrayList;

import net.sf.testium.configuration.LoadDefinitionsXmlHandler;
import net.sf.testium.configuration.SeleniumInterfaceConfiguration.SAVE_SOURCE;
import net.sf.testium.executor.general.SpecifiedParameter;
import net.sf.testium.executor.webdriver.WebInterface;

import org.testtoolinterfaces.testresult.TestStepCommandResult;
import org.testtoolinterfaces.testsuite.ParameterArrayList;
import org.testtoolinterfaces.utils.RunTimeData;

/**
 * Command for defining multiple WebElements.
 * 
 * @author Arjan Kranenburg
 * 
 */
public class LoadElementDefinitions extends GenericSeleniumCommandExecutor {
	private static final SpecifiedParameter PARSPEC_FILE = new SpecifiedParameter(
			"file", String.class, false, true, true, false);

	private static final String COMMAND = "loadDefinitions";

	public LoadElementDefinitions(WebInterface aWebInterface) {
		super(COMMAND, aWebInterface, new ArrayList<SpecifiedParameter>());

		this.addParamSpec(PARSPEC_FILE);
		
		this.setSavePageSource(SAVE_SOURCE.NEVER);
		this.setSaveScreenshot(SAVE_SOURCE.NEVER);
	}

	@Override
	protected void doExecute(RunTimeData aVariables,
			ParameterArrayList parameters, TestStepCommandResult result)
			throws Exception {

		String fileName = (String) obtainValue(aVariables, parameters,
				PARSPEC_FILE);
		
		File definitionsFile = new File ( fileName );
		LoadDefinitionsXmlHandler.loadElementDefinitions( definitionsFile, aVariables, this.getInterface() );
	}
}
