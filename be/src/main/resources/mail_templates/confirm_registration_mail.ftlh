<#-- @ftlvariable name="link" type="String" -->
<!doctype html>
<html lang="de">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bestätigung Ihrer Registrierung</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            background-color: #eef4ff;
            -webkit-text-size-adjust: 100%;
            -ms-text-size-adjust: 100%;
        }

        table {
            border-spacing: 0;
            border-collapse: collapse;
        }

        img {
            border: 0;
            display: block;
            line-height: 100%;
            outline: none;
            text-decoration: none;
        }

        .outer-table {
            width: 100%;
            background-color: #eef4ff;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 100%;
            max-width: 640px;
            margin: 0 auto;
            background-color: #ffffff;
            border-radius: 18px;
            overflow: hidden;
            box-shadow: 0 10px 30px rgba(21, 93, 252, 0.12);
        }

        .header {
            background: linear-gradient(135deg, #155dfc 0%, #2f7cff 100%);
            color: #ffffff;
            padding: 42px 36px 30px;
            text-align: center;
            font-family: Arial, Helvetica, sans-serif;
        }

        .brand {
            display: inline-block;
            font-size: 14px;
            font-weight: bold;
            letter-spacing: 2px;
            text-transform: uppercase;
            opacity: 0.92;
            margin-bottom: 14px;
        }

        .header h1 {
            margin: 0;
            font-size: 30px;
            line-height: 1.2;
            font-weight: 700;
        }

        .header p {
            margin: 14px 0 0;
            font-size: 16px;
            line-height: 1.6;
            opacity: 0.95;
        }

        .content {
            padding: 40px 36px 34px;
            font-family: Arial, Helvetica, sans-serif;
            color: #1f2937;
            font-size: 16px;
            line-height: 1.7;
        }

        .content h2 {
            margin: 0 0 16px;
            font-size: 24px;
            line-height: 1.3;
            color: #0f172a;
        }

        .content p {
            margin: 0 0 16px;
        }

        .highlight-box {
            background-color: #f5f8ff;
            border-left: 4px solid #155dfc;
            border-radius: 12px;
            padding: 18px 20px;
            margin: 24px 0;
        }

        .button-wrap {
            text-align: center;
            margin: 34px 0 22px;
        }

        .button {
            display: inline-block;
            background-color: #155dfc;
            color: #ffffff !important;
            text-decoration: none;
            padding: 14px 30px;
            border-radius: 10px;
            font-weight: 700;
            font-size: 16px;
            box-shadow: 0 8px 18px rgba(21, 93, 252, 0.25);
        }

        .button:hover {
            background-color: #0f4fe0;
        }

        .link-box {
            background-color: #f8fafc;
            border: 1px solid #e5e7eb;
            border-radius: 10px;
            padding: 14px 16px;
            word-break: break-word;
            font-size: 14px;
            color: #334155;
        }

        .link-box a {
            color: #155dfc;
            text-decoration: none;
        }

        .footer {
            padding: 24px 36px 34px;
            text-align: center;
            font-family: Arial, Helvetica, sans-serif;
            font-size: 12px;
            line-height: 1.6;
            color: #64748b;
            background-color: #fbfdff;
        }

        .footer a {
            color: #155dfc;
            text-decoration: none;
        }

        .divider {
            height: 1px;
            background-color: #e5e7eb;
            margin: 0 36px;
        }

        @media only screen and (max-width: 640px) {
            .header,
            .content,
            .footer {
                padding-left: 22px !important;
                padding-right: 22px !important;
            }

            .header h1 {
                font-size: 26px;
            }

            .content h2 {
                font-size: 22px;
            }
        }
    </style>
</head>
<body>
<table class="outer-table" role="presentation" width="100%">
    <tr>
        <td align="center" style="padding: 32px 16px;">
            <table class="container" role="presentation" width="100%">
                <tr>
                    <td class="header">
                        <div class="brand">Movio</div>
                        <h1>Bitte bestätigen Sie Ihre E-Mail-Adresse</h1>
                        <p>Ein letzter Schritt, damit Ihr Konto vollständig aktiviert wird.</p>
                    </td>
                </tr>
                <tr>
                    <td class="content">
                        <h2>Willkommen bei Movio!</h2>
                        <p>Vielen Dank für Ihre Registrierung bei <strong>Movio</strong> – Ihrer modernen Plattform für effiziente digitale Prozesse.</p>

                        <div class="highlight-box">
                            <p style="margin: 0;">
                                Um Ihre Registrierung abzuschließen und Ihr Konto zu aktivieren, bestätigen Sie bitte Ihre E-Mail-Adresse über den folgenden Button.
                            </p>
                        </div>

                        <div class="button-wrap">
                            <a href="${link}" class="button">E-Mail bestätigen</a>
                        </div>

                        <p>Falls der Button nicht funktionieren sollte, verwenden Sie bitte den folgenden Link und fügen ihn in die Adresszeile Ihres Browsers ein:</p>

                        <div class="link-box">
                            <a href="${link}">${link}</a>
                        </div>

                        <p style="margin-top: 22px;">
                            Wenn Sie diese Registrierung nicht selbst angefordert haben, können Sie diese Nachricht einfach ignorieren.
                        </p>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="divider"></div>
                    </td>
                </tr>
                <tr>
                    <td class="footer">
                        <p style="margin: 0 0 8px;">© 2026 Movio (movio.com.ua). Alle Rechte vorbehalten.</p>
                        <p style="margin: 0;">
                            Diese E-Mail wurde automatisch generiert. Bitte antworten Sie nicht direkt auf diese Nachricht.
                        </p>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>
</html>
