/**
 * 
 */
package net.sf.testium.executor.webdriver.commands;

import java.util.ArrayList;

import org.openqa.selenium.WebElement;
import net.sf.testium.executor.general.SpecifiedParameter;
import net.sf.testium.executor.webdriver.WebInterface;
import org.testtoolinterfaces.testresult.TestStepResult;
import org.testtoolinterfaces.testsuite.ParameterArrayList;
import org.testtoolinterfaces.utils.RunTimeData;

/**
 * Executes the Selenium 2.0 click command
 * 
 * @author Arjan Kranenburg
 *
 */
public class Click extends GenericSeleniumCommandExecutor
{
	private static final String COMMAND = "click";

	public static final SpecifiedParameter PARSPEC_ELEMENT = new SpecifiedParameter( 
			"element", WebElement.class, false, false,	true, true );

	public Click( WebInterface aWebInterface ) {
		super( COMMAND, aWebInterface, new ArrayList<SpecifiedParameter>() );

		this.addParamSpec( PARSPEC_ELEMENT );
	}

	@Override
	protected void doExecute(RunTimeData aVariables,
			ParameterArrayList parameters, TestStepResult result)
			throws Exception {

		WebElement element = obtainElement(aVariables, parameters, PARSPEC_ELEMENT);
		element.click();
	}
}
