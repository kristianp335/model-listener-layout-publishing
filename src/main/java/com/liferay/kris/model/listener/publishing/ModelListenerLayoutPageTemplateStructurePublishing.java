package com.liferay.kris.model.listener.publishing;

import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
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
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;


/**
 * @author Akos Thurzo
 */
@Component(immediate = true, service = ModelListener.class)
public class ModelListenerLayoutPageTemplateStructurePublishing extends BaseModelListener<LayoutPageTemplateStructureRel> {
	
	

	@Override	
	public void onAfterUpdate(LayoutPageTemplateStructureRel layoutPageTemplateStructureRel) throws ModelListenerException {
ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		
		
        long experienceId = layoutPageTemplateStructureRel.getSegmentsExperienceId();
        
        if (experienceId != 0)
        {
	        long parentId = layoutPageTemplateStructureRel.getLayoutPageTemplateStructureId();
	        
	        String experienceName = "";
	        SegmentsExperience segmentsExperience;
			try {
				segmentsExperience = segmentsExperienceLocalService.getSegmentsExperience(experienceId);
				experienceName = segmentsExperience.getName();
			} catch (PortalException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			long layoutId = 0;
			
			try {
				LayoutPageTemplateStructure layoutPageTemplateStructure = layoutPageTemplateStructureLocalService.getLayoutPageTemplateStructure(parentId);
				layoutId = layoutPageTemplateStructure.getClassPK();
			} catch (PortalException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
	
			//ContentEditor
			//long userId = 35403;
			
			//Content Manager
			long userId = 37627;
			
			String layoutName = "";
			String layoutFriendlyUrl = "";
			try {
				Layout layout = layoutLocalService.getLayout(layoutId);
				layoutFriendlyUrl = layout.getFriendlyURL();
				layoutName = layout.getName();
				
				
			} catch (PortalException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}		
			
			//you want to make this dynamic in reality
			String path = "/web/guest";
			String linkUrl = path + layoutFriendlyUrl;
			
			
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
			jsonObject.put("notificationBody", "An experience design was updated. The experience was <i>" + experienceName + "</i>, and the page was <i>" + layoutName + "</i>.");
			jsonObject.put("notificationText", "An experience design was updated." );
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
	}	
	
	@Override	
	public void onAfterCreate(LayoutPageTemplateStructureRel layoutPageTemplateStructureRel) throws ModelListenerException {
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		
        long experienceId = layoutPageTemplateStructureRel.getSegmentsExperienceId();
        if (experienceId != 0)
        {
	        long parentId = layoutPageTemplateStructureRel.getLayoutPageTemplateStructureId();
	        
	        String experienceName = "";
	        SegmentsExperience segmentsExperience;
			try {
				segmentsExperience = segmentsExperienceLocalService.getSegmentsExperience(experienceId);
				experienceName = segmentsExperience.getName();
			} catch (PortalException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			long layoutId = 0;
			
			try {
				LayoutPageTemplateStructure layoutPageTemplateStructure = layoutPageTemplateStructureLocalService.getLayoutPageTemplateStructure(parentId);
				layoutId = layoutPageTemplateStructure.getClassPK();
			} catch (PortalException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
	
			//ContentEditor
			//long userId = 35403;
			
			//Content Manager
			long userId = 37627;
			
			String layoutName = "";
			String layoutFriendlyUrl = "";
			try {
				Layout layout = layoutLocalService.getLayout(layoutId);
				layoutFriendlyUrl = layout.getFriendlyURL();
				layoutName = layout.getName();
				
				
			} catch (PortalException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}		
			
			//you want to make this dynamic in reality
			String path = "/web/guest";
			String linkUrl = path + layoutFriendlyUrl;
			
			
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
			jsonObject.put("notificationBody", "An experience design was created. The experience was <i>" + experienceName + "</i>, and the page was <i>" + layoutName + "</i>.");
			jsonObject.put("notificationText", "An experience design was created." );
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
	}
	
	@Reference
	LayoutLocalService layoutLocalService;
	
	@Reference
	SegmentsExperienceLocalService segmentsExperienceLocalService;
	
	@Reference	
	LayoutPageTemplateStructureLocalService layoutPageTemplateStructureLocalService;
	
	
		
	private UserNotificationEventLocalService userNotificationEventLocalService;
	
	@Reference(unbind = "-")
	public void setUserNotificationEventLocalService(
			UserNotificationEventLocalService userNotificationEventLocalService) {
		this.userNotificationEventLocalService = userNotificationEventLocalService;
	}
}