/*
 * Copyright 2010-2016 JetBrains s.r.o.
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

package org.jetbrains.kotlin.ir.visitors

import org.jetbrains.kotlin.ir.IrElement
import org.jetbrains.kotlin.ir.declarations.*
import org.jetbrains.kotlin.ir.expressions.*

interface IrElementTransformer<in D> : IrElementVisitor<IrElement, D> {
    override fun visitElement(element: IrElement, data: D): IrElement {
        element.transformChildren(this, data)
        return element
    }

    override fun visitModuleFragment(declaration: IrModuleFragment, data: D): IrModuleFragment {
        declaration.transformChildren(this, data)
        return declaration
    }

    override fun visitFile(declaration: IrFile, data: D): IrFile {
        declaration.transformChildren(this, data)
        return declaration
    }

    override fun visitDeclaration(declaration: IrDeclaration, data: D): IrDeclaration {
        declaration.transformChildren(this, data)
        return declaration
    }

    override fun visitClass(declaration: IrClass, data: D) = visitDeclaration(declaration, data)
    override fun visitTypeAlias(declaration: IrTypeAlias, data: D) = visitDeclaration(declaration, data)
    override fun visitFunction(declaration: IrFunction, data: D) = visitDeclaration(declaration, data)
    override fun visitConstructor(declaration: IrConstructor, data: D) = visitFunction(declaration, data)
    override fun visitProperty(declaration: IrProperty, data: D) = visitDeclaration(declaration, data)
    override fun visitField(declaration: IrField, data: D) = visitDeclaration(declaration, data)
    override fun visitLocalDelegatedProperty(declaration: IrLocalDelegatedProperty, data: D) = visitDeclaration(declaration, data)
    override fun visitEnumEntry(declaration: IrEnumEntry, data: D) = visitDeclaration(declaration, data)
    override fun visitAnonymousInitializer(declaration: IrAnonymousInitializer, data: D) = visitDeclaration(declaration, data)
    override fun visitVariable(declaration: IrVariable, data: D) = visitDeclaration(declaration, data)

    override fun visitBody(body: IrBody, data: D): IrBody {
        body.transformChildren(this, data)
        return body
    }

    override fun visitExpressionBody(body: IrExpressionBody, data: D) = visitBody(body, data)
    override fun visitBlockBody(body: IrBlockBody, data: D) = visitBody(body, data)
    override fun visitSyntheticBody(body: IrSyntheticBody, data: D) = visitBody(body, data)

    override fun visitExpression(expression: IrExpression, data: D): IrExpression {
        expression.transformChildren(this, data)
        return expression
    }

    override fun <T> visitConst(expression: IrConst<T>, data: D) = visitExpression(expression, data)
    override fun visitVararg(expression: IrVararg, data: D) = visitExpression(expression, data)
    override fun visitSpreadElement(spread: IrSpreadElement, data: D): IrSpreadElement = spread

    override fun visitContainerExpression(expression: IrContainerExpression, data: D) = visitExpression(expression, data)
    override fun visitBlock(expression: IrBlock, data: D) = visitContainerExpression(expression, data)
    override fun visitComposite(expression: IrComposite, data: D) = visitContainerExpression(expression, data)
    override fun visitStringConcatenation(expression: IrStringConcatenation, data: D) = visitExpression(expression, data)
    override fun visitThisReference(expression: IrThisReference, data: D) = visitExpression(expression, data)

    override fun visitDeclarationReference(expression: IrDeclarationReference, data: D) = visitExpression(expression, data)
    override fun visitSingletonReference(expression: IrGetSingletonValue, data: D) = visitDeclarationReference(expression, data)
    override fun visitGetObjectValue(expression: IrGetObjectValue, data: D) = visitSingletonReference(expression, data)
    override fun visitGetEnumValue(expression: IrGetEnumValue, data: D) = visitSingletonReference(expression, data)
    override fun visitVariableAccess(expression: IrVariableAccessExpression, data: D) = visitDeclarationReference(expression, data)
    override fun visitGetVariable(expression: IrGetVariable, data: D) = visitVariableAccess(expression, data)
    override fun visitSetVariable(expression: IrSetVariable, data: D) = visitVariableAccess(expression, data)
    override fun visitFieldAccess(expression: IrFieldAccessExpression, data: D) = visitDeclarationReference(expression, data)
    override fun visitGetField(expression: IrGetField, data: D) = visitFieldAccess(expression, data)
    override fun visitSetField(expression: IrSetField, data: D) = visitFieldAccess(expression, data)
    override fun visitGetExtensionReceiver(expression: IrGetExtensionReceiver, data: D) = visitDeclarationReference(expression, data)
    override fun visitGeneralCall(expression: IrGeneralCall, data: D) = visitDeclarationReference(expression, data)
    override fun visitCall(expression: IrCall, data: D) = visitGeneralCall(expression, data)
    override fun visitDelegatingConstructorCall(expression: IrDelegatingConstructorCall, data: D) = visitGeneralCall(expression, data)
    override fun visitEnumConstructorCall(expression: IrEnumConstructorCall, data: D) = visitGeneralCall(expression, data)
    override fun visitGetClass(expression: IrGetClass, data: D) = visitExpression(expression, data)

    override fun visitCallableReference(expression: IrCallableReference, data: D) = visitGeneralCall(expression, data)
    override fun visitClassReference(expression: IrClassReference, data: D) = visitDeclarationReference(expression, data)

    override fun visitInstanceInitializerCall(expression: IrInstanceInitializerCall, data: D) = visitExpression(expression, data)

    override fun visitTypeOperator(expression: IrTypeOperatorCall, data: D) = visitExpression(expression, data)

    override fun visitWhen(expression: IrWhen, data: D) = visitExpression(expression, data)
    override fun visitLoop(loop: IrLoop, data: D) = visitExpression(loop, data)
    override fun visitWhileLoop(loop: IrWhileLoop, data: D) = visitLoop(loop, data)
    override fun visitDoWhileLoop(loop: IrDoWhileLoop, data: D) = visitLoop(loop, data)
    override fun visitTry(aTry: IrTry, data: D) = visitExpression(aTry, data)

    override fun visitCatch(aCatch: IrCatch, data: D): IrCatch {
        aCatch.transformChildren(this, data)
        return aCatch
    }

    override fun visitBreakContinue(jump: IrBreakContinue, data: D) = visitExpression(jump, data)
    override fun visitBreak(jump: IrBreak, data: D) = visitBreakContinue(jump, data)
    override fun visitContinue(jump: IrContinue, data: D) = visitBreakContinue(jump, data)

    override fun visitReturn(expression: IrReturn, data: D) = visitExpression(expression, data)
    override fun visitThrow(expression: IrThrow, data: D) = visitExpression(expression, data)

    override fun visitErrorDeclaration(declaration: IrErrorDeclaration, data: D) = visitDeclaration(declaration, data)
    override fun visitErrorExpression(expression: IrErrorExpression, data: D) = visitExpression(expression, data)
    override fun visitErrorCallExpression(expression: IrErrorCallExpression, data: D) = visitErrorExpression(expression, data)
}
