package com.hsousa_apps.Autocarros.fragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.hsousa_apps.Autocarros.R
import com.hsousa_apps.Autocarros.WebViewActivity
import com.hsousa_apps.Autocarros.data.Functions

class SettingsFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Functions().checkForCustomAd(view, requireActivity())

        val notDeveloped: Button = view.findViewById(R.id.button_not_developed)
        val rate: Button = view.findViewById(R.id.button_rate_app)
        val patreon: Button = view.findViewById(R.id.button_support_creator)
        val mail: TextView = view.findViewById(R.id.report_mail)
        val busContacts: Button = view.findViewById(R.id.button_bus_contact)
        val faqs: Button = view.findViewById(R.id.button_faq)
        val sousadevLogo: ImageView = view.findViewById(R.id.sousadev_logo)
        val openWebViewButton: Button = view.findViewById(R.id.btnOpenWebView) // New Button

        // Set up existing button listeners
        sousadevLogo.setOnClickListener {
            Toast.makeText(
                context,
                resources.getString(R.string.toast_link_message),
                Toast.LENGTH_SHORT
            ).show()

            val url = "https://linktr.ee/sousadev_"
            openWebPage(url)
        }

        faqs.setOnClickListener {
            val builder = context?.let { AlertDialog.Builder(it) }
            builder?.setTitle(getString(R.string.faq_label))
            builder?.setMessage(
                getString(R.string.faq1_question) + "\n\t\t" + getString(R.string.faq1_answer)
                        + "\n\n" + getString(R.string.faq2_question) + "\n\t\t" + getString(R.string.faq2_answer)
            )
            builder?.setPositiveButton(android.R.string.yes) { dialog, which ->
            }
            builder?.show()
        }

        busContacts.setOnClickListener {
            showContactsDialog()
        }

        notDeveloped.setOnClickListener {
            showWarningDialog()
        }

        rate.setOnClickListener {
            rateApp()
        }

        patreon.setOnClickListener {
            openPatreonPage()
        }

        mail.setOnClickListener {
            sendEmail()
        }

        // Handle the new Open WebView button
        openWebViewButton.setOnClickListener {
            navigateToWebViewActivity()
        }

        // Check network connectivity and toggle button visibility
        if (Functions().isOnline(requireContext())) {
            openWebViewButton.visibility = View.VISIBLE
        } else {
            openWebViewButton.visibility = View.GONE
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                // Handle other button clicks if needed
            }
        }
    }

    /**
     * Utility function to open a web page.
     */
    private fun openWebPage(url: String) {
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(url)
                )
            )
        } catch (anfe: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(url)
                )
            )
        }
    }

    /**
     * Show the Contacts Dialog
     */
    @RequiresApi(Build.VERSION_CODES.N)
    private fun showContactsDialog() {
        val builder = context?.let { AlertDialog.Builder(it) }
        builder?.setTitle(getString(R.string.company_contacts_label))
        builder?.setMessage(
            Html.fromHtml(
                """
                <b>AutoViação Micaelense</b>
                +351 296 301 350
                <a href='http://www.autoviacaomicaelense.pt'>autoviacaomicaelense.pt</a>

                <b>Varela E Cª Lda.</b>
                +351 296 301 800
                <a href='https://www.grupobensaude.pt/en/business-areas/services/varela-servicos/'>grupobensaude.pt</a>

                <b>CRP - Caetano, Raposo E Pereiras Lda.</b>
                +351 296 304 260
                <a href='https://www.crp-caetanoraposopereiras.pt'>crp-caetanoraposopereiras.pt</a>
                """.trimIndent(),
                Html.FROM_HTML_MODE_LEGACY
            )
        )

        builder?.setPositiveButton(android.R.string.yes) { dialog, which ->
            // Do nothing or handle positive action
        }

        builder?.show()
    }

    /**
     * Show a Warning Dialog
     */
    private fun showWarningDialog() {
        val builder = context?.let { AlertDialog.Builder(it) }
        builder?.setTitle(getString(R.string.warning_dialog_title))
        builder?.setMessage(getString(R.string.warning_dialog_message))

        builder?.setPositiveButton(android.R.string.yes) { dialog, which ->
            // Do nothing or handle positive action
        }

        builder?.show()
    }

    /**
     * Handle App Rating
     */
    private fun rateApp() {
        Toast.makeText(
            context,
            resources.getString(R.string.toast_link_message),
            Toast.LENGTH_SHORT
        ).show()

        val appPackageName: String? = activity?.packageName

        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$appPackageName")
                )
            )
        } catch (anfe: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                )
            )
        }
    }

    /**
     * Open Patreon Page
     */
    private fun openPatreonPage() {
        Toast.makeText(
            context,
            resources.getString(R.string.toast_link_message),
            Toast.LENGTH_SHORT
        ).show()

        val url = "https://linktr.ee/sousadev_"
        openWebPage(url)
    }

    /**
     * Send Email
     */
    private fun sendEmail() {
        val intent = Intent(
            Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "info@saomiguelbus.com", null
            )
        )
        intent.putExtra(Intent.EXTRA_SUBJECT, "São Miguel Bus: [Problem]")
        startActivity(Intent.createChooser(intent, "Choose an Email client:"))
    }

    /**
     * Navigate to WebViewActivity
     */
    private fun navigateToWebViewActivity() {
        val intent = Intent(requireActivity(), WebViewActivity::class.java)
        startActivity(intent)
    }
}