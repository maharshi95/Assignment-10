package com.mars.findbugs;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.BytecodeScanningDetector;
import edu.umd.cs.findbugs.Priorities;
import edu.umd.cs.findbugs.classfile.CheckedAnalysisException;
import edu.umd.cs.findbugs.classfile.ClassDescriptor;
import edu.umd.cs.findbugs.classfile.DescriptorFactory;
import edu.umd.cs.findbugs.classfile.MethodDescriptor;

/**
 * Created by maharshigor on 22/07/16.
 */
public class RunnableBugDetector extends BytecodeScanningDetector {

	private BugReporter reporter;

	public RunnableBugDetector(BugReporter reporter) {
		this.reporter = reporter;
	}

	@Override
	public void sawMethod() {
		MethodDescriptor invokedMethod = getMethodDescriptorOperand ( );
		ClassDescriptor invokedObject = getClassDescriptorOperand ( );
		String methodName = invokedMethod.getName ( );
		if (methodName != null && methodName.equals ("run")) {
			try {
				Class c = Class.forName (invokedObject.getDottedClassName ( ));
				ClassDescriptor[] interfaceDescriptors = invokedObject.getXClass ( ).getInterfaceDescriptorList ( );
				boolean found = false;
				for (ClassDescriptor interfaceDescriptor : interfaceDescriptors) {
					if(Runnable.class.isAssignableFrom (Class.forName (interfaceDescriptor.getDottedClassName ())))
						found = true;
				}
				if (found) {
					reporter.reportBug (
							new BugInstance ("RUNNABLE_IN_SELF_THREAD_BUG", Priorities.NORMAL_PRIORITY)
									.addClass (this)
									.addMethod (this)
									.addSourceLine (this)
					);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace ( );
			} catch (CheckedAnalysisException e) {
				e.printStackTrace ( );
			}
		}
	}

	public void report() {

	}
}
