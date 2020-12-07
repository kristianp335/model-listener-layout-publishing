package com.liferay.kris.model.listener.publishing;

import aQute.bnd.annotation.metatype.Meta;

/**
 * Configuration options for Kris' LayoutPage-Notification-ModelListeners 
 * @author Olaf Kock 
 */

@Meta.OCD(
	    id = "com.liferay.kris.model.listener.publishing.ModelListenerConfiguration"
	    , localization = "content/Language"
	    , name = "model-listener-layout-publishing-configuration"
	)
public interface ModelListenerConfiguration {

	@Meta.AD(
			deflt = "35403",
			description = "model-listener-layout-publishing-content-editor-description",
			name = "model-listener-layout-publishing-content-editor",
			required = false
			)
	public long contentEditor();

	@Meta.AD(
			deflt = "37627",
			description = "model-listener-layout-publishing-content-manager-description",
			name = "model-listener-layout-publishing-content-manager",
			required = false
			)
	public long contentManager();

}
