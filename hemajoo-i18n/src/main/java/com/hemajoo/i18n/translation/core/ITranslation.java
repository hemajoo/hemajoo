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
package com.hemajoo.i18n.translation.core;

import com.hemajoo.i18n.core.localization.data.LanguageType;
import com.hemajoo.i18n.translation.core.entity.ITranslationEntity;
import lombok.NonNull;

import java.io.Serializable;
import java.util.List;

public interface ITranslation extends Serializable
{
    /**
     * Return the number of translation entities (files) this translation contains.
     * @return Number of translation entities this translation contains.
     */
    int countEntities();

    /**
     * Count the number of entries to translate.
     * @return Number of entries to translate.
     */
    int countToTranslate();

    /**
     * Count the number of entries translated.
     * @return Number of entries translated.
     */
    int countTranslated();

    /**
     * Count the number of entries having failed being translated.
     * @return Number of entries having failed .
     */
    int countFailed();

    boolean needTranslation();

    long getExecutionTime();

    void addExecutionTime(final long time);

    /**
     * Return if a translation entity exist for the given language type.
     * @param language Language.
     * @return <b>True</b> if an entity exist for the given language type, <b>false</b> otherwise.
     */
    boolean existEntity(final LanguageType language);

    /**
     * Return the source translation entity.
     * @return Source {@link ITranslationEntity}.
     */
    ITranslationEntity getSourceEntity();

    /**
     * Return the list of target translation entities.
     * @return Translation entities.
     */
    List<ITranslationEntity> getTargetEntities();

    /**
     * Return a target translation entity for a given language.
     * @param language Language.
     * @return Target {@link ITranslationEntity} if found, <b>null</b> otherwise.
     */
    ITranslationEntity getTargetEntity(final LanguageType language);

    /**
     * Add a target translation entity.
     * @param entity Target translation entity.
     */
    void addTargetEntity(final @NonNull ITranslationEntity entity);

    void removeTargetEntity(LanguageType language);

    void clearTargetEntities();
}
