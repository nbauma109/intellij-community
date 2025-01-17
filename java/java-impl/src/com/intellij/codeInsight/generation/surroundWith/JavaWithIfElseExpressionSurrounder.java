
/*
 * Copyright 2000-2009 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.codeInsight.generation.surroundWith;

import com.intellij.codeInsight.CodeInsightUtilCore;
import com.intellij.java.JavaBundle;
import com.intellij.modcommand.ActionContext;
import com.intellij.modcommand.ModPsiUpdater;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.CodeStyleManager;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class JavaWithIfElseExpressionSurrounder extends JavaWithIfExpressionSurrounder{

  @Override
  protected void surroundExpression(@NotNull ActionContext context, @NotNull PsiExpression expr, @NotNull ModPsiUpdater updater) {
    Project project = context.project();
    PsiElementFactory factory = JavaPsiFacade.getElementFactory(project);
    CodeStyleManager codeStyleManager = CodeStyleManager.getInstance(project);

    @NonNls String text = "if(a){\nst;\n}else{\n}";
    PsiIfStatement ifStatement = (PsiIfStatement)factory.createStatementFromText(text, null);
    ifStatement = (PsiIfStatement)codeStyleManager.reformat(ifStatement);

    Objects.requireNonNull(ifStatement.getCondition()).replace(expr);

    PsiExpressionStatement statement = (PsiExpressionStatement)expr.getParent();
    ifStatement = (PsiIfStatement)statement.replace(ifStatement);

    PsiCodeBlock block = ((PsiBlockStatement)ifStatement.getThenBranch()).getCodeBlock();

    PsiStatement afterStatement = CodeInsightUtilCore.forcePsiPostprocessAndRestoreElement(block.getStatements()[0]);

    TextRange range = afterStatement.getTextRange();
    afterStatement.getContainingFile().getFileDocument().deleteString(range.getStartOffset(), range.getEndOffset());
    updater.moveCaretTo(range.getStartOffset());
  }

  @Override
  public String getTemplateDescription() {
    return JavaBundle.message("surround.with.ifelse.expression.template");
  }
}