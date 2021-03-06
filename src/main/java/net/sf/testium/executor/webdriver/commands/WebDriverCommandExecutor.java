/**
 * 
 */
package net.sf.testium.executor.webdriver.commands;

import java.io.File;

import org.openqa.selenium.WebDriver;
import net.sf.testium.configuration.SeleniumConfiguration.BROWSER_TYPE;
import net.sf.testium.executor.TestStepCommandExecutor;
import net.sf.testium.executor.webdriver.WebInterface;
import org.testtoolinterfaces.testresult.TestStepResult;
import org.testtoolinterfaces.testsuite.Parameter;
import org.testtoolinterfaces.testsuite.ParameterArrayList;
import org.testtoolinterfaces.testsuite.ParameterImpl;
import org.testtoolinterfaces.testsuite.ParameterVariable;
import org.testtoolinterfaces.testsuite.TestStep;
import org.testtoolinterfaces.testsuite.TestSuiteException;
import org.testtoolinterfaces.utils.RunTimeData;

/**
 * @author Arjan Kranenburg
 *
 */
public abstract class WebDriverCommandExecutor implements TestStepCommandExecutor
{
	private final String myCommand;
	private final WebInterface myInterface;

	abstract public TestStepResult execute( TestStep aStep,
	                                        RunTimeData aVariables,
	                                        File aLogDir )
					throws TestSuiteException;

	abstract public boolean verifyParameters( ParameterArrayList aParameters )
					throws TestSuiteException;

    /**
	 * 
	 */
	public WebDriverCommandExecutor( String aCommand, WebInterface aWebInterface )
	{
		myCommand = aCommand;
		myInterface = aWebInterface;
	}

	protected WebInterface getInterface()
	{
		return myInterface;
	}

	public String getCommand()
	{
		return myCommand;
	}

	public String getInterfaceName()
	{
		return myInterface.getInterfaceName();
	}

	@Deprecated
	protected WebDriver getDriver( BROWSER_TYPE aBrowserType )
	{
		WebDriver webDriver = myInterface.getDriver( aBrowserType );
		
		return webDriver;
	}

	protected WebDriver getDriver()
	{
		return myInterface.getDriver();
	}

	/**
	 * @param aPar
	 * @param aType
	 * @throws TestSuiteException
	 */
	protected void verifyParameterValue(Parameter aPar, Class<? extends Object> aType) throws TestSuiteException
	{
		if ( aPar == null )
		{
			throw new TestSuiteException( "Parameter is not set",
			                              getInterfaceName() + "." + getCommand() );
		}

		String parName = aPar.getName();
		if ( ! ParameterImpl.class.isInstance( aPar ) )
		{
			throw new TestSuiteException( "Parameter " + parName + " is not a value",
			                              getInterfaceName() + "." + getCommand() );
		}

		ParameterImpl parameter = (ParameterImpl) aPar;
		if ( ! parameter.getValueType().equals( aType ) )
		{
			throw new TestSuiteException( "Parameter " + parName + " must be a " + aType.getSimpleName(),
			                              getInterfaceName() + "." + getCommand() );
		}

		if ( aType == String.class )
		{
			if ( parameter.getValueAsString().isEmpty() )
			{
				throw new TestSuiteException( parName + " cannot be empty",
				                              getInterfaceName() + "." + getCommand() );
			}
		}
	}

	/**
	 * @param elementPar
	 * @throws TestSuiteException
	 */
	protected void verifyParameterVariable( Parameter aPar ) throws TestSuiteException
	{
		if ( aPar == null )
		{
			throw new TestSuiteException( "Parameter is not set",
			                              getInterfaceName() + "." + getCommand() );
		}
		
		String parName = aPar.getName();
		if ( ! ParameterVariable.class.isInstance(aPar) )
		{
			throw new TestSuiteException( "Parameter " + parName + " is not defined as a variable",
			                              getInterfaceName() + "." + getCommand() );
		}

		if ( ((ParameterVariable) aPar).getVariableName().isEmpty() )
		{
			throw new TestSuiteException( "Variable name of " + parName + " cannot be empty",
			                              getInterfaceName() + "." + getCommand() );
		}
	}

	/**
	 * @param aVariables
	 * @param aPar
	 * @return
	 * @throws TestSuiteException
	 */
	protected <Type> Type getVariableValueAs( Class<Type> aType, Parameter aPar, RunTimeData aVariables )
																				throws TestSuiteException
	{
		Type valueOfType = null;
		ParameterVariable parVariable = (ParameterVariable) aPar;
		String variableName = parVariable.getVariableName();

		if ( ! aVariables.containsKey(variableName) )
		{
			throw new TestSuiteException( "Variable " + variableName + " is not set",
			                              getInterfaceName() + "." + getCommand() );
		}

		valueOfType = aVariables.getValueAs( aType, variableName);
		if ( valueOfType == null )
		{
			throw new TestSuiteException( "Variable " + variableName + " is not of type " + aType.getSimpleName(),
			                              getInterfaceName() + "." + getCommand() );
		}
		return valueOfType;
	}
}
