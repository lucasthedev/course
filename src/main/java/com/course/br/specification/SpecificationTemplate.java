package com.course.br.specification;

import com.course.br.entity.CourseEntity;
import com.course.br.entity.LessonEntity;
import com.course.br.entity.ModuleEntity;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.UUID;

public class SpecificationTemplate {

    @And({
            @Spec(path = "name", spec = Like.class),
            @Spec(path = "courseStatus", spec = Equal.class),
            @Spec(path = "levelStatus", spec = Equal.class)
    })
    public interface CourseSpecification extends Specification<CourseEntity>{}

    @Spec(path = "title", spec = Equal.class)
    public interface ModuleSpecification extends Specification<ModuleEntity>{}

    @Spec(path = "title", spec = Equal.class)
    public interface LessonSpecification extends Specification<LessonEntity>{}

    public static Specification<ModuleEntity> moduleCourseId(final UUID courseId){
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            Root<ModuleEntity> module = root;
            Root<CourseEntity> course = query.from(CourseEntity.class);
            Expression<Collection<ModuleEntity>> coursesModules = course.get("modules");
            return criteriaBuilder.and(criteriaBuilder.equal(course.get("courseId"), courseId), criteriaBuilder.isMember(module, coursesModules));
        };
    }

    public static Specification<LessonEntity> lessonModuleId(final UUID moduleId){
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            Root<LessonEntity> lesson = root;
            Root<ModuleEntity> module = query.from(ModuleEntity.class);
            Expression<Collection<LessonEntity>> moduleLessons = module.get("lessons");
            return criteriaBuilder.and(criteriaBuilder.equal(module.get("moduleId"), moduleId), criteriaBuilder.isMember(lesson, moduleLessons));
        };
    }
}
