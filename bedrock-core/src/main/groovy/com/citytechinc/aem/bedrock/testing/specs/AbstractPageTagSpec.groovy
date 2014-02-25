/**
 * Copyright 2014, CITYTECH, Inc.
 * All rights reserved - Do Not Redistribute
 * Confidential and Proprietary
 */
package com.citytechinc.aem.bedrock.testing.specs

import com.citytechinc.aem.spock.specs.tag.AbstractTagSpec
import com.citytechinc.cq.groovy.extension.builders.NodeBuilder
import com.citytechinc.cq.groovy.extension.builders.PageBuilder
import com.citytechinc.cq.groovy.extension.metaclass.GroovyExtensionMetaClassRegistry
import com.citytechinc.aem.bedrock.content.page.PageManagerDecorator
import com.citytechinc.aem.bedrock.content.page.impl.DefaultPageManagerDecorator
import spock.lang.Shared

import static com.day.cq.wcm.tags.DefineObjectsTag.DEFAULT_CURRENT_PAGE_NAME

/**
 * Spock specification for testing CQ page-based tag support classes.
 */
abstract class AbstractPageTagSpec extends AbstractTagSpec {

    @Shared pageBuilder

    @Shared nodeBuilder

    @Override
    void addResourceResolverAdapters() {
        addResourceResolverAdapter(PageManagerDecorator, {
            resourceResolver -> new DefaultPageManagerDecorator(resourceResolver)
        })
    }

    def setupSpec() {
        GroovyExtensionMetaClassRegistry.registerMetaClasses()

        pageBuilder = new PageBuilder(session)
        nodeBuilder = new NodeBuilder(session)
    }

    /**
     * Set a <code>PageDecorator</code> for the given path in the <code>PageContext</code> for the tag under test.
     *
     * @param path page path
     */
    void setupPage(path) {
        def page = resourceResolver.adaptTo(PageManagerDecorator).getPage(path)

        tag.pageContext.getAttribute(DEFAULT_CURRENT_PAGE_NAME) >> page
    }
}