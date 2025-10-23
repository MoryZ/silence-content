// model.ftl
package com.old.silence.domain.model;

    import jakarta.persistence.Column;
    import jakarta.persistence.Entity;
    import jakarta.persistence.OneToMany;
import com.old.silence.domain.enums.PoetryUserStatus;
import com.old.silence.commons.domain.AbstractAuditable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

@Entity
public class PoetryUser extends AbstractAuditable&lt;igInteger> {
            private FreeMarker template error (DEBUG mode; use RETHROW in production!):
Expression has thrown an unchecked exception; see the cause exception.
The blamed expression:
==> getJavaType(column)  [in template "model.ftl" at line 28, column 23]

----
FTL stack trace ("~" means nesting-related):
	- Failed at: ${getJavaType(column)}  [in template "model.ftl" at line 28, column 21]
----

Java stack trace (for programmers):
----
freemarker.core._MiscTemplateException: [... Exception message was already printed; see it above ...]
	at freemarker.core.Expression.eval(Expression.java:106)
	at freemarker.core.DollarVariable.calculateInterpolatedStringOrMarkup(DollarVariable.java:104)
	at freemarker.core.DollarVariable.accept(DollarVariable.java:63)
	at freemarker.core.Environment.visit(Environment.java:344)
	at freemarker.core.Environment.visit(Environment.java:386)
	at freemarker.core.IteratorBlock$IterationContext.executedNestedContentForCollOrSeqListing(IteratorBlock.java:321)
	at freemarker.core.IteratorBlock$IterationContext.executeNestedContent(IteratorBlock.java:271)
	at freemarker.core.IteratorBlock$IterationContext.accept(IteratorBlock.java:244)
	at freemarker.core.Environment.visitIteratorBlock(Environment.java:654)
	at freemarker.core.IteratorBlock.acceptWithResult(IteratorBlock.java:108)
	at freemarker.core.IteratorBlock.accept(IteratorBlock.java:94)
	at freemarker.core.Environment.visit(Environment.java:344)
	at freemarker.core.Environment.visit(Environment.java:350)
	at freemarker.core.Environment.process(Environment.java:323)
	at freemarker.template.Template.process(Template.java:383)
	at com.old.silence.code.generator.SpringCodeGenerator.generateFile(SpringCodeGenerator.java:46)
	at com.old.silence.code.generator.APIGeneratorMain.generateCodeFiles(APIGeneratorMain.java:134)
	at com.old.silence.code.generator.APIGeneratorMain.generateForTables(APIGeneratorMain.java:112)
	at com.old.silence.code.generator.APIGeneratorMain.generateAPI(APIGeneratorMain.java:63)
	at com.old.silence.code.generator.APIGeneratorMain.main(APIGeneratorMain.java:22)
Caused by: java.lang.ClassCastException: class freemarker.ext.beans.GenericObjectModel cannot be cast to class com.old.silence.code.generator.model.ColumnInfo (freemarker.ext.beans.GenericObjectModel and com.old.silence.code.generator.model.ColumnInfo are in unnamed module of loader 'app')
	at com.old.silence.code.generator.SpringCodeGenerator$TypeConverterMethod.exec(SpringCodeGenerator.java:152)
	at freemarker.core.MethodCall._eval(MethodCall.java:62)
	at freemarker.core.Expression.eval(Expression.java:101)
	... 19 more
