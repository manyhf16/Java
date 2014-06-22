package zpark.ext.hibernate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

/**
 * 代理类工厂注册器
 * 
 * <p>
 * 因为每个dao接口需要一个代理类工厂，因此为了方便起见，不是在spring配置文件中一个个注册，而是利用此类统一注册代理类工厂
 * </p>
 * 
 * @author yihang
 * 
 */
public class GenericDaoRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

	private static Logger logger = LoggerFactory.getLogger(GenericDaoRegistryPostProcessor.class);

	private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

	private MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);

	private String genericDaoInterface = "zpark.ext.hibernate.GenericDao";

	public void setGenericDaoInterface(String genericDaoInterface) {
		this.genericDaoInterface = genericDaoInterface;
	}

	private String daoPackageToScan;

	public void setDaoPackageToScan(String daoPackageToScan) {
		this.daoPackageToScan = daoPackageToScan;
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		try {
			String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
					+ ClassUtils.convertClassNameToResourcePath(daoPackageToScan) + "/**/*.class";
			Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
			for (Resource r : resources) {
				// 得到每个class类的Meta信息
				MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(r);
				ClassMetadata classMetadata = metadataReader.getClassMetadata();
				if (classMetadata.isInterface()) {
					String[] interfaceNames = classMetadata.getInterfaceNames();
					for (String name : interfaceNames) {
						// 如果实现了泛型接口，注册代理类工厂
						if (genericDaoInterface.equals(name)) {
							Class<?> c = ClassUtils.forName(classMetadata.getClassName(), resourcePatternResolver.getClassLoader());
							BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(GenericDaoProxyFactoryBean.class);
							builder.addConstructorArgValue(c);
							builder.addConstructorArgReference("hibernateTemplateExt");
							registry.registerBeanDefinition(ClassUtils.getShortNameAsProperty(c), builder.getBeanDefinition());
						}
					}
				}
			}
		} catch (Exception e) {
			throw new FatalBeanException("BeanDefinition Error:", e);
		}

	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		logger.info("--------------beans begin--------------");
		for (String n : beanFactory.getBeanDefinitionNames()) {
			logger.info(n);
		}
		logger.info("--------------beans end--------------");
	}

}
