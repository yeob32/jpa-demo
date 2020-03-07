# Jpa-Demo

> JPA 프로그래밍 ( 김영한 ) 샘플


## Querydsl 설정

정적 타입 지원 :
``` groovy
def querydslSrcDir = 'src/main/generated'

querydsl {
    library = "com.querydsl:querydsl-apt"
    jpa = true
    querydslSourcesDir = querydslSrcDir
}

compileQuerydsl {
    options.annotationProcessorPath = configurations.querydsl
}

configurations {
    querydsl.extendsFrom compileClasspath
}

sourceSets {
    main {
        java {
            srcDirs = ['src/main/java', querydslSrcDir]
        }
    }
}
```

컴파일 진행 : 
```$xslt
Gradle > Tasks > other > compileQuerydsl
```


