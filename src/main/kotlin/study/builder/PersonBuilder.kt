package study.builder

import study.domain.Languages
import study.domain.Person
import study.domain.Skills

class PersonBuilder() {
    private var name: String = ""
    private var company: String = ""
    private var skills: Skills = Skills()
    private var languages: Languages = Languages()
    fun name(value: String) {
        name = value
    }

    fun company(value: String) {
        company = value
    }

    fun skills(block: Skills.() -> Unit) {
        skills = Skills().apply(block)
    }

    fun languages(block: Languages.() -> Unit) {
        languages = Languages().apply(block)
    }

    fun build(): Person {
        return Person(name, company, skills, languages)
    }
}
