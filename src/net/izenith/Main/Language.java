package net.izenith.Main;

public enum Language {

	AFRIKAANS("af"),
	ALBANIAN("sq"),
	ARABIC("ar"),
	ARMENIAN("hy"),
	BASQUE("eu"),
	BELARUSIAN("be"),
	BULGARIAN("bg"),
	CATALAN("ca"),
	CHINESE("zh"),
	CROATION("hr"),
	CZECH("cs"),
	DANISH("da"),
	DUTCH("nl"),
	ESTONIAN("et"),
	FINNISH("fi"),
	FRENCH("fr"),
	GALICIAN("gl"),
	GEORGIAN("ka"),
	GERMAN("de"),
	GREEK("el"),
	HEBREW("he"),
	HUNGARIAN("hu"),
	ICELANDIC("is"),
	INDONESIAN("id");
	
	private String code;
	
	private Language(String code){
		this.code = code;
	}
	
}
