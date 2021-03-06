package com.citytechinc.aem.bedrock.core.tags

import spock.lang.Unroll

@Unroll
class TitleTagSpec extends AbstractMetaTagSpec {

    def setupSpec() {
        pageBuilder.content {
            citytechinc("CITYTECH, Inc.") {
                "jcr:content"(pageTitle: "CITYTECH")
            }
            ctmsp {
                "jcr:content"(pageTitle: "CTMSP")
            }
        }
    }

    def "title variations"() {
        setup:
        def tag = new TitleTag()

        tag.propertyName = propertyName
        tag.suffix = suffix

        def jspTag = init(tag, "/content/citytechinc")

        when:
        tag.doEndTag()

        then:
        jspTag.output == html

        where:
        propertyName | suffix           | html
        ""           | ""               | "<title>CITYTECH, Inc.</title>"
        ""           | " | Chicago, IL" | "<title>CITYTECH, Inc. | Chicago, IL</title>"
        "pageTitle"  | ""               | "<title>CITYTECH</title>"
        "pageTitle"  | " | Chicago, IL" | "<title>CITYTECH | Chicago, IL</title>"
        "navTitle"   | ""               | "<title>CITYTECH, Inc.</title>"
        "navTitle"   | " | Chicago, IL" | "<title>CITYTECH, Inc. | Chicago, IL</title>"
    }

    def "empty title defaults to page name"() {
        setup:
        def tag = new TitleTag()
        def jspTag = init(tag, "/content/ctmsp")

        when:
        tag.doEndTag()

        then:
        jspTag.output == "<title>ctmsp</title>"
    }
}
