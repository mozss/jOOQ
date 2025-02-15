/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Other licenses:
 * -----------------------------------------------------------------------------
 * Commercial licenses for this work are available. These replace the above
 * ASL 2.0 and offer limited warranties, support, maintenance, and commercial
 * database integrations.
 *
 * For more information, please visit: https://www.jooq.org/legal/licensing
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package org.jooq.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.jooq.BindContext;
import org.jooq.Configuration;
import org.jooq.ExecuteContext;
import org.jooq.Field;

/**
 * @author Lukas Eder
 */
final class DefaultBindContext extends AbstractBindContext {

    DefaultBindContext(Configuration configuration, ExecuteContext ctx, PreparedStatement stmt) {
        super(configuration, ctx, stmt);
    }

    @Override
    @SuppressWarnings({ "unchecked" })
    protected final BindContext bindValue0(Object value, Field<?> field) throws SQLException {
        int nextIndex = nextIndex();

        try {
            ((Field<Object>) field).getBinding().set(
                new DefaultBindingSetStatementContext<>(new SimpleExecuteContext(configuration(), data()), stmt, nextIndex, value)
            );
        }

        // [#14696] Maintain SQLState and ErrorCode if the exception is from the driver
        catch (SQLException e) {
            throw new SQLException("Error while writing value at JDBC bind index: " + nextIndex, e.getSQLState(), e.getErrorCode(), e);
        }
        catch (Exception e) {
            throw new SQLException("Error while writing value at JDBC bind index: " + nextIndex, e);
        }

        return this;
    }
}
