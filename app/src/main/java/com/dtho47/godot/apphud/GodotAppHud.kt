package com.dtho47.godot.apphud

import android.util.Log
import com.apphud.sdk.Apphud
import org.godotengine.godot.Godot
import org.godotengine.godot.plugin.GodotPlugin
import org.godotengine.godot.plugin.SignalInfo
import org.godotengine.godot.plugin.UsedByGodot

class GodotAppHud(godot: Godot) : GodotPlugin(godot) {
    private val tag: String = "GodotAppHud"

    override fun getPluginName(): String = tag

    override fun getPluginSignals(): Set<SignalInfo> = emptySet()

    /**
     * Keep the historical Godot API unchanged while adapting the call to the current Apphud SDK.
     */
    @UsedByGodot
    fun start(apiKey: String, userID: String, isObserver: Boolean) {
        Apphud.start(
            context = godot.requireContext(),
            apiKey = apiKey,
            userId = userID,
            observerMode = isObserver,
            callback = { user ->
                Log.i(tag, "Successful! User id ${user.userId}")
            }
        )
        Apphud.collectDeviceIdentifiers()
    }

    /**
     * Historical manual initialization entry point. The Godot signature intentionally stays the same.
     */
    @UsedByGodot
    fun start_manual(apiKey: String, userID: String, deviceID: String, isObserver: Boolean) {
        Apphud.start(
            context = godot.requireContext(),
            apiKey = apiKey,
            userId = userID,
            deviceId = deviceID,
            observerMode = isObserver,
            callback = { user ->
                Log.i(tag, "Successful! User id ${user.userId}")
            }
        )
        Apphud.collectDeviceIdentifiers()
    }

    @UsedByGodot
    fun get_user_id(): String = Apphud.userId().orEmpty()

    @UsedByGodot
    fun get_device_id(): String = Apphud.deviceId().orEmpty()

    @UsedByGodot
    fun track_purchase(productId: String, offerIdToken: String, paywallId: String, placementId: String) {
        val paywallIdentifier = paywallId.ifEmpty { null }
        val placementIdentifier = placementId.ifEmpty { null }

        Apphud.trackPurchase(
            productId = productId,
            offerIdToken = offerIdToken,
            paywallIdentifier = paywallIdentifier,
            placementIdentifier = placementIdentifier
        )
    }

    @UsedByGodot
    fun logout() {
        Apphud.logout()
    }
}
