package com.liferay.kris.model.listener.publishing;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.segments.model.SegmentsEntry;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true, service = ModelListener.class)
public class ModelListenerSegmentPublishing extends BaseModelListener<SegmentsEntry> {
	
	

	@Override	
	public void onAfterUpdate(SegmentsEntry segmentsEntry) throws ModelListenerException {
ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		
		//ContentEditor
		long userId = 35403;
		
		//Content Manager
		//long userId = 37627;
		
		//you want to make this dynamic in reality
		String path = "/group/guest/~/control_panel/manage?p_p_id=com_liferay_layout_admin_web_portlet_GroupPagesPortlet&p_p_lifecycle=0&p_p_state=maximized&p_v_l_s_g_id=20124";
		String linkUrl = path;
		
		
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		jsonObject.put("notificationBody", "An experience segment was updated. The segment was <i>" + segmentsEntry.getName() + "</i>.");
		jsonObject.put("notificationText", "An exoerience segment was updated." );
		jsonObject.put("senderName", "Content Managers");
		jsonObject.put("linkUrl", linkUrl);
		
		
		// Send notification
		try {
			userNotificationEventLocalService.addUserNotificationEvent(userId,
					"com_liferay_kris_model_listener_publishing", (new Date()).getTime(),
					UserNotificationDeliveryConstants.TYPE_WEBSITE, userId, jsonObject.toJSONString(), false,
					serviceContext);
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
	}	
	
	@Override	
	public void onAfterCreate(SegmentsEntry segmentsEntry) throws ModelListenerException {
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		
		//ContentEditor
	    long userId = 35403;
		
		//Content Manager
		//long userId = 37627;
		
		//you want to make this dynamic in reality
		String path = "/group/guest/~/control_panel/manage?p_p_id=com_liferay_layout_admin_web_portlet_GroupPagesPortlet&p_p_lifecycle=0&p_p_state=maximized&p_v_l_s_g_id=20124";
		String linkUrl = path;
		
		
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		jsonObject.put("notificationBody", "An experience segment was created. The segment was <i>" + segmentsEntry.getName() + "</i>.");
		jsonObject.put("notificationText", "An experience segment was created." );
		jsonObject.put("senderName", "Content Managers");
		jsonObject.put("linkUrl", linkUrl);
		
		
		// Send notification
		try {
			userNotificationEventLocalService.addUserNotificationEvent(userId,
					"com_liferay_kris_model_listener_publishing", (new Date()).getTime(),
					UserNotificationDeliveryConstants.TYPE_WEBSITE, userId, jsonObject.toJSONString(), false,
					serviceContext);
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
	}	
	
	
		
	private UserNotificationEventLocalService userNotificationEventLocalService;
	
	@Reference(unbind = "-")
	public void setUserNotificationEventLocalService(
			UserNotificationEventLocalService userNotificationEventLocalService) {
		this.userNotificationEventLocalService = userNotificationEventLocalService;
	}
}