(function() {
    'use strict';

    angular
        .module('epressingApp', [
            'ngStorage',
            'tmh.dynamicLocale',
            'pascalprecht.translate',
            'ngResource',
            'ngCookies',
            'ngAria',
            'ngCacheBuster',
            'ngFileUpload',
            'ui.bootstrap',
            'ui.bootstrap.datetimepicker',
            'ui.router',
            'infinite-scroll',
            // jhipster-needle-angularjs-add-module JHipster will add new module here
            'angular-loading-bar'
        ])
        .run(run);

    run.$inject = ['stateHandler', 'translationHandler','$rootScope'];

    function run(stateHandler, translationHandler,$rootScope) {
        stateHandler.initialize();
        translationHandler.initialize();  
        console.log('execution de $rootScope.lignesCommandes = []; ')  
        $rootScope.lignesCommandes = [];
        console.log('Intruction $rootScope.lignesCommandes = []; executée')  
        $rootScope.maCommande = {
                'id': null,
                'dateCommande': new Date() ,
                'dateFacture': null,
                'dateFacturation': null,
                'dateCueillette': null,
                'dateLivraison': null,
                'netAPayer': 0,
                'etat': 'En Attente',
                'adresseCueillette': null,
                'adresseLivraison': null,
                'adresseFacturation': null,
                'carteBancaire': null,
                'client': null
            };
    }
})();
