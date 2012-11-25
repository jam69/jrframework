package com.jrsolutions.framework.generator;

import com.jrsolutions.framework.core.utils.constrainedpanel.ConstraintsParser;
import java.util.ArrayList;
import java.util.List;

public class DefinitionsParser {

	public DefinitionsParser() {
	}

	public Object exec(List lista){
		if (lista.size() < 1)
			return "";
		String definition = (String) lista.get(0);
		if (definition == null || definition.length() == 0)
			return "";
		ConstraintsParser p = new ConstraintsParser();
		return p.parse(definition);
	}

	public ArrayList parser(String definition){
		if (definition == null || definition.length() == 0)
			return null;
		ConstraintsParser p = new ConstraintsParser();
		return p.parse(definition);
	}
}
