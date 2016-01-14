package com.hc.test.framework.entities;

import java.io.Serializable;

public abstract class Domain<K> implements Serializable
{
	protected static final long serialVersionUID = 1L;
	
	protected String message;
	
	protected String error;

	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object object)
	{
		if (object == this)
		{
			return true;
		}

		if (object == null || object.getClass() != this.getClass())
		{
			return false;
		}

		if (getId() == null)
		{
			return false;
		}

		return getId().equals(((Domain<K>) object).getId());
	}
	
	

	public abstract K getId();

	public abstract void setId(K id);



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}



	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}



	/**
	 * @param error the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}
}
