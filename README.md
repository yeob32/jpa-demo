# Jpa-tutorial

> JPA 프로그래밍 ( 김영한 )

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

## 참고
- 실무에서는 JPA와 스프링 데이터 JPA 기본으로 사용
- 복잡한 동적 쿼리는 Querydsl 라이브러리 사용
- 위 조합으로 해결하기 어려운 쿼리는 JPA가 제공하는 네이티브 쿼리를 사용, 또는 JdbcTemplate 사용
