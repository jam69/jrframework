package com.jrsolutions.framework.core.metamodel.creators;

import java.util.Random;

public class FloatCreator implements DataCreator {

	static Random rnd = new Random();

	public Object create(String[] par) {
		return new Float(rnd.nextFloat());
	}

}
