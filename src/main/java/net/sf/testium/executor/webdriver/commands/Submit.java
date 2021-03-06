/**
 * 
 */
package net.sf.testium.executor.webdriver.commands;

import java.util.ArrayList;

import net.sf.testium.executor.general.SpecifiedParameter;
import net.sf.testium.executor.webdriver.WebInterface;

import org.openqa.selenium.WebElement;
import org.testtoolinterfaces.testresult.TestStepCommandResult;
import org.testtoolinterfaces.testsuite.ParameterArrayList;
import org.testtoolinterfaces.utils.RunTimeData;

/**
 * Command for checking the text of a WebElement
 * 
 * @author Arjan Kranenburg
 *
 */
public class Submit extends GenericSeleniumCommandExecutor
{
	private static final SpecifiedParameter PARSPEC_ELEMENT = new SpecifiedParameter( 
			"element", WebElement.class, false, false, true, false );

	private static final String COMMAND = "submit";

	public Submit( WebInterface aWebInterface ) {
		super( COMMAND, aWebInterface, new ArrayList<SpecifiedParameter>() );

		this.addParamSpec( PARSPEC_ELEMENT );
	}

	@Override
	protected void doExecute(RunTimeData aVariables,
			ParameterArrayList parameters, TestStepCommandResult result)
			throws Exception {

		WebElement element = obtainElement(aVariables, parameters, PARSPEC_ELEMENT);
		if( element == null )
		{
			throw new Exception( "Mandatory element was null" );
		}

		element.submit();
	}
}
