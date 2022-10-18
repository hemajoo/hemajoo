/*
 * (C) Copyright Hemajoo Systems Inc. 2021-2022 - All Rights Reserved
 * -----------------------------------------------------------------------------------------------
 * All information contained herein is, and remains the property of
 * Hemajoo Inc. and its suppliers, if any. The intellectual and technical
 * concepts contained herein are proprietary to Hemajoo Systems Inc.
 * and its suppliers and may be covered by U.S. and Foreign Patents, patents
 * in process, and are protected by trade secret or copyright law.
 *
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained from
 * Hemajoo Systems Inc.
 * -----------------------------------------------------------------------------------------------
 */
package com.hemajoo.i18n.translation;

import com.hemajoo.i18n.localization.data.LanguageType;
import com.hemajoo.i18n.translation.entity.ITranslationEntity;
import com.hemajoo.i18n.translation.entry.ITranslationEntry;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Translation implements ITranslation
{
    @Getter
    private final ITranslationEntity source;

    @Singular
    private final List<ITranslationEntity> targets = new ArrayList<>();

    @Getter
    private long executionTime;

    @Builder(setterPrefix = "with")
    public Translation(final @NonNull ITranslationEntity source, final @NonNull ITranslationEntity target)
    {
        this.source = source;
        this.targets.add(target);
        executionTime = 0L;
    }

    @Override
    public final int countEntities()
    {
        int count = 0;
        for (ITranslationEntity entity : targets)
        {
            count += entity.getEntries().size();
        }

        return count;
    }

    @Override
    public final int countToTranslate()
    {
        int count = 0;
        for (ITranslationEntity entity : targets)
        {
            for (ITranslationEntry entry : entity.getEntries())
            {
                if (entry.isToTranslate())
                {
                    ++count;
                }
            }
        }

        return count;
    }

    @Override
    public final int countTranslated()
    {
        int count = 0;
        for (ITranslationEntity entity : targets)
        {
            for (ITranslationEntry entry : entity.getEntries())
            {
                if (entry.isTranslated())
                {
                    ++count;
                }
            }
        }

        return count;
    }

    @Override
    public final int countFailed()
    {
        int count = 0;
        for (ITranslationEntity entity : targets)
        {
            for (ITranslationEntry entry : entity.getEntries())
            {
                if (entry.isFailed())
                {
                    ++count;
                }
            }
        }

        return count;
    }

    @Override
    public final boolean needTranslation()
    {
        return getTargetEntities().stream()
                .anyMatch(entity -> entity.getEntries().stream()
                        .anyMatch(ITranslationEntry::isToTranslate));
    }

    @Override
    public final void addExecutionTime(long time)
    {
        if (time > 0)
        {
            executionTime += time;
        }
    }

    @Override
    public final boolean existEntity(final LanguageType language)
    {
        return targets.stream().anyMatch(e -> e.getLanguage() == language);
    }

    @Override
    public final ITranslationEntity getSourceEntity()
    {
        return source;
    }

    @Override
    public final List<ITranslationEntity> getTargetEntities()
    {
        return targets;
    }

    @Override
    public final ITranslationEntity getTargetEntity(final LanguageType language)
    {
        return targets.stream().filter(e -> e.getLanguage() == language).findFirst().orElse(null);
    }

    @Override
    public final void addTargetEntity(final @NonNull ITranslationEntity entity)
    {
        if (existEntity(entity.getLanguage()))
        {
            LOGGER.warn(String.format("Replacing target entity with language: %s", entity.getLanguage()));
        }

        targets.add(entity);
    }

    @Override
    public final void removeTargetEntity(final LanguageType language)
    {
        targets.removeIf(e -> e.getLanguage() == language);
    }

    @Override
    public final void clearTargetEntities()
    {
        targets.clear();
    }

}
