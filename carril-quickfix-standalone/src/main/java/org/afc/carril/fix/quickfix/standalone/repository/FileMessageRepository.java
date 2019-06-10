package org.afc.carril.fix.quickfix.standalone.repository;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.afc.util.FileUtil;
import org.afc.util.StringUtil;

public class FileMessageRepository implements MessageRepository {

	private static final Logger logger = LoggerFactory.getLogger(FileMessageRepository.class);
	
	private List<File> files;
	
	protected String buffer[];
	
	protected int index; 
	
	private String breakPattern;

	private boolean breakInclude;

	private String path;
	
	public FileMessageRepository(String path) {
		this(path, "\\r\\n|\\n", false);
	}
	
	public FileMessageRepository(String path, String breakPattern, boolean breakInclude) {
		this.breakPattern = breakPattern;
		this.breakInclude = breakInclude;
		this.path = path;
		load();
	}
	
	@Override
	public String next() {
		return (index < buffer.length) ? buffer[index++] : null;
	}

	protected void load() {
		files = new LinkedList<>();
		aggregate(files, new File(path));
		buffer = files.stream()
			.map(f -> {
				logger.info("loading:[{}]", f);
				return f;
			})
			.map(f -> FileUtil.readFileAsString(f.getPath()))
			.flatMap(this::parse)
			.collect(Collectors.toList())
			.toArray(new String[0]);
	}
	
	private Stream<String> parse(String content) {
		List<String> lines = new LinkedList<>();
		Pattern pattern = Pattern.compile(breakPattern);
		Matcher matcher = pattern.matcher(content);

		int start = 0;
		while (matcher.find()) {
			add(lines, content.substring(start, matcher.start()));
			start = breakInclude ? matcher.start() : matcher.end();
		}		
		add(lines, content.substring(start));
		return lines.stream();
	}
	
	private static void add(List<String> lines, String line) {
		String trim = line.trim().replaceAll("(^#|\n#|\r#).*?(?=\r|\n|$)", "").replaceAll("\\|(\r*\n*)*", "|");
		if (StringUtil.hasValue(trim)) {
			lines.add(trim);
		}
	}
	
	private static void aggregate(List<File> files, File file) {
		if (file.isFile()) {
			if (!file.getName().startsWith(".")) {
				files.add(file);
			}
		} else if (!file.exists()){
			throw new RuntimeException(file + " is not exist!");
		} else {
			for (File subFile : file.listFiles()) {
				aggregate(files, subFile);
			}
		}
	}
	
	@Override
	public String toString() {
		return "size:" + buffer.length + ", files:" + files.toString();
	}
}
