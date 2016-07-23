package com.mars.findbugs;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.BytecodeScanningDetector;
import edu.umd.cs.findbugs.Priorities;
import edu.umd.cs.findbugs.classfile.ClassDescriptor;
import edu.umd.cs.findbugs.classfile.DescriptorFactory;
import edu.umd.cs.findbugs.classfile.MethodDescriptor;

/**
 * Created by maharshigor on 22/07/16.
 */
public class ThreadBugDetector extends BytecodeScanningDetector {
	private BugReporter reporter;
	public ThreadBugDetector(BugReporter reporter) {
		this.reporter = reporter;
	}

	@Override
	public void sawMethod() {
		MethodDescriptor invokedMethod = getMethodDescriptorOperand();
		ClassDescriptor invokedObject = getClassDescriptorOperand();
		String methodName = invokedMethod.getName ();
		if(methodName != null && methodName.equals ("run")) try {
			Class c = Class.forName (invokedObject.getDottedClassName ( ));
			if (Thread.class.isAssignableFrom (c)) {
				reporter.reportBug (
						new BugInstance ("THREAD_RUN_BUG", Priorities.HIGH_PRIORITY)
								.addClass (this)
								.addMethod (this)
								.addSourceLine (this)
				);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace ( );
		}
	}

	public void report() {

	}
}
