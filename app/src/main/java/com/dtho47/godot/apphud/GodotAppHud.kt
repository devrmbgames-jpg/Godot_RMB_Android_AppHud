package com.dtho47.godot.apphud

import android.util.Log
import com.android.billingclient.api.BillingFlowParams
import com.apphud.sdk.Apphud
import com.apphud.sdk.domain.ApphudUser
import org.godotengine.godot.Godot
import org.godotengine.godot.plugin.GodotPlugin
import org.godotengine.godot.plugin.SignalInfo
import org.godotengine.godot.plugin.UsedByGodot
import org.godotengine.godot.Dictionary

class GodotAppHud(godot: Godot) : GodotPlugin(godot) {
    private val tag: String = "GodotAppHud"

    //private val signalPricesUpdate: SignalInfo = SignalInfo("prices_in_app_update", Object::class.java)

    override fun getPluginName(): String {
        return tag
    }

    override fun getPluginSignals(): Set<SignalInfo> {
        return setOf()
    }


    @UsedByGodot
    fun start(apiKey: String, userID: String, isObserver: Boolean) {
        Apphud.start(godot.requireContext(), apiKey, userID, isObserver) {it ->
            Log.i(tag, "Successful! User id ${it.userId}")
        }
        Apphud.collectDeviceIdentifiers()
        BillingFlowParams.newBuilder().setObfuscatedAccountId(Apphud.deviceId())
    }

    @UsedByGodot
    fun start_manual(apiKey: String, userID: String, deviceID: String, isObserver: Boolean) {
        Apphud.start(godot.requireContext(), apiKey, userID, deviceID, isObserver) {it ->
            Log.i(tag, "Successful! User id ${it.userId}")
        }
        Apphud.collectDeviceIdentifiers()
        BillingFlowParams.newBuilder().setObfuscatedAccountId(Apphud.deviceId())
    }


    @UsedByGodot
    fun get_user_id() : String {
        return Apphud.userId()
    }

    @UsedByGodot
    fun get_device_id() : String {
        return Apphud.deviceId()
    }

    @UsedByGodot
    fun track_purchase(productId:String, offerIdToken:String, paywallId:String, placementId: String) {

        var paywallIdentifier: String? = null
        if (paywallId.isNotEmpty()) {
            paywallIdentifier = paywallId
        }

        var placementIdentifier: String? = null
        if (placementId.isNotEmpty()) {
            placementIdentifier = placementId
        }


        Apphud.trackPurchase(
            productId,
            offerIdToken,
            paywallIdentifier,
            placementIdentifier
        )
    }


    @UsedByGodot
    fun logout() {
        Apphud.logout()
    }


}