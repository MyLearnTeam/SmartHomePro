package com.qcode365.project.core.shiro;

import org.apache.shiro.spring.config.web.autoconfigure.ShiroWebFilterConfiguration;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
//===================版权说明=======================/
//本快速开发框架系统 版权归 快码猿所有                                                                    /
//本框架源代码由快码猿提供                               /
//注册商标：快码猿 QCode                              / 
//备注时间：2019-10-01                              / 
//===============================================/

@Configuration
public class ShiroConfiguration extends ShiroWebFilterConfiguration{
	@Override
    protected ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean factoryBean = super.shiroFilterFactoryBean();
        Map<String,Filter> filterMap = factoryBean.getFilters();
        //添加自定义的Filter,这里我随便new了一个filter
        filterMap.put("authc",new LoginShiroFilter());
        factoryBean.setFilters(filterMap);
        return factoryBean;
    }

	// 将自己的验证方式加入容器
	@Bean
	public CustomRealm myShiroRealm() {
		CustomRealm shiroRealm = new CustomRealm();
		shiroRealm.setCachingEnabled(true);
		// 启用身份验证缓存，即缓存AuthenticationInfo信息，默认false
		shiroRealm.setAuthenticationCachingEnabled(true);
		// 缓存AuthenticationInfo信息的缓存名称 在ehcache-shiro.xml中有对应缓存的配置
		shiroRealm.setAuthenticationCacheName("authenticationCache");
		// 启用授权缓存，即缓存AuthorizationInfo信息，默认false
		shiroRealm.setAuthorizationCachingEnabled(true);
		// 缓存AuthorizationInfo信息的缓存名称 在ehcache-shiro.xml中有对应缓存的配置
		shiroRealm.setAuthorizationCacheName("authorizationCache");
		return shiroRealm;
	}

	/**
	 * 配置核心安全事务管理器
	 * @param shiroRealm
	 * @return
	 */
	@Bean(name = "securityManager")
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 设置自定义realm.
		List<Realm> realms = new ArrayList<>();
		// 设置多Realm
		realms.add(myShiroRealm());
		securityManager.setRealms(realms);
		// 配置记住我 
		// securityManager.setRememberMeManager(rememberMeManager());

		// 配置 ehcache缓存管理器 
		securityManager.setCacheManager(ehCacheManager());

		// 配置自定义session管理，
		// securityManager.setSessionManager(sessionManager());

		return securityManager;
	}

	/*
	 * //权限管理，配置主要是Realm的管理认证
	 */
	@Bean
	public ShiroFilterChainDefinition shiroFilterChainDefinition() {
		DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
		chainDefinition.addPathDefinition("/resources/**", "anon");
		chainDefinition.addPathDefinition("/jsp/login.jsp", "anon");
		chainDefinition.addPathDefinition("/sysLogin", "anon");
		chainDefinition.addPathDefinition("/jsp/login", "anon");
		chainDefinition.addPathDefinition("/getimage*", "anon");
		chainDefinition.addPathDefinition("/logout", "anon");
		chainDefinition.addPathDefinition("/impl/**", "anon");
		chainDefinition.addPathDefinition("/**", "authc,perms");
		return chainDefinition;
	}

	// 加入注解的使用，不加入这个注解不生效
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	@Bean
	public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		/**
		 * setUsePrefix(false)用于解决一个奇怪的bug。在引入spring aop的情况下。
		 * 在@Controller注解的类的方法中加入@RequiresRole等shiro注解，会导致该方法无法映射请求，导致返回404。
		 * 加入这项配置能解决这个bug
		 */
		defaultAdvisorAutoProxyCreator.setUsePrefix(true);
		return defaultAdvisorAutoProxyCreator;
	}

	/**
	 * shiro缓存管理器; 需要添加到securityManager中
	 * 
	 * @return
	 */
	@Bean
	public EhCacheManager ehCacheManager() {
		EhCacheManager cacheManager = new EhCacheManager();
		cacheManager.setCacheManagerConfigFile("classpath:shiro-ehcache.xml");
		return cacheManager;
	}
	/**
	 * 让某个实例的某个方法的返回值注入为Bean的实例
	 * Spring静态注入
	 * @return
	 */
	@Bean
	public MethodInvokingFactoryBean getMethodInvokingFactoryBean(){
	    MethodInvokingFactoryBean factoryBean = new MethodInvokingFactoryBean();
	    factoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
	    factoryBean.setArguments(new Object[]{securityManager()});
	    return factoryBean;
	}
}
