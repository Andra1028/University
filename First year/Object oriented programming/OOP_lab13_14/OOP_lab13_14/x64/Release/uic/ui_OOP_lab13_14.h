/********************************************************************************
** Form generated from reading UI file 'OOP_lab13_14.ui'
**
** Created by: Qt User Interface Compiler version 6.2.4
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_OOP_LAB13_14_H
#define UI_OOP_LAB13_14_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QMenuBar>
#include <QtWidgets/QStatusBar>
#include <QtWidgets/QToolBar>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_OOP_lab13_14Class
{
public:
    QMenuBar *menuBar;
    QToolBar *mainToolBar;
    QWidget *centralWidget;
    QStatusBar *statusBar;

    void setupUi(QMainWindow *OOP_lab13_14Class)
    {
        if (OOP_lab13_14Class->objectName().isEmpty())
            OOP_lab13_14Class->setObjectName(QString::fromUtf8("OOP_lab13_14Class"));
        OOP_lab13_14Class->resize(600, 400);
        menuBar = new QMenuBar(OOP_lab13_14Class);
        menuBar->setObjectName(QString::fromUtf8("menuBar"));
        OOP_lab13_14Class->setMenuBar(menuBar);
        mainToolBar = new QToolBar(OOP_lab13_14Class);
        mainToolBar->setObjectName(QString::fromUtf8("mainToolBar"));
        OOP_lab13_14Class->addToolBar(mainToolBar);
        centralWidget = new QWidget(OOP_lab13_14Class);
        centralWidget->setObjectName(QString::fromUtf8("centralWidget"));
        OOP_lab13_14Class->setCentralWidget(centralWidget);
        statusBar = new QStatusBar(OOP_lab13_14Class);
        statusBar->setObjectName(QString::fromUtf8("statusBar"));
        OOP_lab13_14Class->setStatusBar(statusBar);

        retranslateUi(OOP_lab13_14Class);

        QMetaObject::connectSlotsByName(OOP_lab13_14Class);
    } // setupUi

    void retranslateUi(QMainWindow *OOP_lab13_14Class)
    {
        OOP_lab13_14Class->setWindowTitle(QCoreApplication::translate("OOP_lab13_14Class", "OOP_lab13_14", nullptr));
    } // retranslateUi

};

namespace Ui {
    class OOP_lab13_14Class: public Ui_OOP_lab13_14Class {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_OOP_LAB13_14_H
