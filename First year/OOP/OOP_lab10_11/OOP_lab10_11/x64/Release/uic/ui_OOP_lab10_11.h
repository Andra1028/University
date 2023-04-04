/********************************************************************************
** Form generated from reading UI file 'OOP_lab10_11.ui'
**
** Created by: Qt User Interface Compiler version 6.3.0
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_OOP_LAB10_11_H
#define UI_OOP_LAB10_11_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QMenuBar>
#include <QtWidgets/QStatusBar>
#include <QtWidgets/QToolBar>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_OOP_lab10_11Class
{
public:
    QMenuBar *menuBar;
    QToolBar *mainToolBar;
    QWidget *centralWidget;
    QStatusBar *statusBar;

    void setupUi(QMainWindow *OOP_lab10_11Class)
    {
        if (OOP_lab10_11Class->objectName().isEmpty())
            OOP_lab10_11Class->setObjectName(QString::fromUtf8("OOP_lab10_11Class"));
        OOP_lab10_11Class->resize(600, 400);
        menuBar = new QMenuBar(OOP_lab10_11Class);
        menuBar->setObjectName(QString::fromUtf8("menuBar"));
        OOP_lab10_11Class->setMenuBar(menuBar);
        mainToolBar = new QToolBar(OOP_lab10_11Class);
        mainToolBar->setObjectName(QString::fromUtf8("mainToolBar"));
        OOP_lab10_11Class->addToolBar(mainToolBar);
        centralWidget = new QWidget(OOP_lab10_11Class);
        centralWidget->setObjectName(QString::fromUtf8("centralWidget"));
        OOP_lab10_11Class->setCentralWidget(centralWidget);
        statusBar = new QStatusBar(OOP_lab10_11Class);
        statusBar->setObjectName(QString::fromUtf8("statusBar"));
        OOP_lab10_11Class->setStatusBar(statusBar);

        retranslateUi(OOP_lab10_11Class);

        QMetaObject::connectSlotsByName(OOP_lab10_11Class);
    } // setupUi

    void retranslateUi(QMainWindow *OOP_lab10_11Class)
    {
        OOP_lab10_11Class->setWindowTitle(QCoreApplication::translate("OOP_lab10_11Class", "OOP_lab10_11", nullptr));
    } // retranslateUi

};

namespace Ui {
    class OOP_lab10_11Class: public Ui_OOP_lab10_11Class {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_OOP_LAB10_11_H
