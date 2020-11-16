package com.liferay.kris.model.listener.publishing;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.segments.model.SegmentsExperience;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true, service = ModelListener.class)
public class ModelListenerExperiencePublishing extends BaseModelListener<SegmentsExperience> {
	
	

	@Override	
	public void onAfterUpdate(SegmentsExperience segmentsExperience) throws ModelListenerException {
ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		
		//ContentEditor
		//long userId = 35403;
		
		//Content Manager
		long userId = 37627;
		
		long thisLayoutId = segmentsExperience.getClassPK();
		String layoutFriendlyUrl = "";
		try {
			Layout layout = layoutLocalService.getLayout(thisLayoutId);
			layoutFriendlyUrl = layout.getFriendlyURL();
			
		} catch (PortalException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		
		//you want to make this dynamic in reality
		String path = "/web/guest";
		String linkUrl = path + layoutFriendlyUrl;
		
		
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		jsonObject.put("notificationBody", "An experience was updated. The experience was <i>" + segmentsExperience.getName() + "</i>.");
		jsonObject.put("notificationText", "An experience was updated." );
		jsonObject.put("senderName", "Content Editors");
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
	public void onAfterCreate(SegmentsExperience segmentsExperience) throws ModelListenerException {
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		
		//ContentEditor
		//long userId = 35403;
		
		//Content Manager
		long userId = 37627;
		
		long thisLayoutId = segmentsExperience.getClassPK();
		String layoutFriendlyUrl = "";
		try {
			Layout layout = layoutLocalService.getLayout(thisLayoutId);
			layoutFriendlyUrl = layout.getFriendlyURL();
			
		} catch (PortalException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		//you want to make this dynamic in reality
		String path = "/web/guest";
		String linkUrl = path + layoutFriendlyUrl;
		
		
		
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		jsonObject.put("notificationBody", "An experience was created. The experience was <i>" + segmentsExperience.getName() + "</i>.");
		jsonObject.put("notificationText", "An experience was created." );
		jsonObject.put("senderName", "Content Editors");
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
	
	@Reference
	LayoutLocalService layoutLocalService;
		
	private UserNotificationEventLocalService userNotificationEventLocalService;
	
	@Reference(unbind = "-")
	public void setUserNotificationEventLocalService(
			UserNotificationEventLocalService userNotificationEventLocalService) {
		this.userNotificationEventLocalService = userNotificationEventLocalService;
	}
}