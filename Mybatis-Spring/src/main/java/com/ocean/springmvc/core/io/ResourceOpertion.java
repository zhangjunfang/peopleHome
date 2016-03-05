package com.ocean.springmvc.core.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import org.springframework.core.io.Resource;

public class ResourceOpertion implements Resource {

	private Resource resource;

	@Override
	public InputStream getInputStream() throws IOException {
		
		return this.resource.getInputStream();
	}

	@Override
	public boolean exists() {
		return this.resource.exists();
	}

	@Override
	public boolean isReadable() {
		return this.resource.isReadable();
	}

	@Override
	public boolean isOpen() {
		return this.resource.isOpen();
	}

	@Override
	public URL getURL() throws IOException {
		return this.resource.getURL();
	}

	@Override
	public URI getURI() throws IOException {
		return this.resource.getURI();
	}

	@Override
	public File getFile() throws IOException {
		return this.resource.getFile();
	}

	@Override
	public long contentLength() throws IOException {
		return this.resource.contentLength();
	}

	@Override
	public long lastModified() throws IOException {
		return this.resource.lastModified();
	}

	@Override
	public Resource createRelative(String relativePath) throws IOException {
		return this.resource.createRelative(relativePath);
	}

	@Override
	public String getFilename() {
		return this.resource.getFilename();
	}

	@Override
	public String getDescription() {
		return this.resource.getDescription();
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}
}