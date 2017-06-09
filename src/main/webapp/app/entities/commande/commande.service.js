(function() {
    'use strict';
    angular
        .module('epressingApp')
        .factory('Commande', Commande);

    Commande.$inject = ['$resource', 'DateUtils'];

    function Commande ($resource, DateUtils) {
        var resourceUrl =  'api/commandes/:id';
        var commandsOfCurrentUser = 'api/commandes/user/:id'
        var operationUrl = 'api/commandes/commandeForms';
        var detailCommandeUrl = 'api/commandes/commandeComplete/:id'

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'getAllCommandsOfCurrentUser': { url: commandsOfCurrentUser, method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateCommande = DateUtils.convertDateTimeFromServer(data.dateCommande);
                        data.dateFacture = DateUtils.convertDateTimeFromServer(data.dateFacture);
                        data.dateFacturation = DateUtils.convertDateTimeFromServer(data.dateFacturation);
                        data.dateCueillette = DateUtils.convertDateTimeFromServer(data.dateCueillette);
                        data.dateLivraison = DateUtils.convertDateTimeFromServer(data.dateLivraison);
                    }
                    return data;
                }
            },
            'getCommandeComplete': {
                url: detailCommandeUrl,
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.commande.dateCommande = DateUtils.convertDateTimeFromServer(data.commande.dateCommande);
                        data.commande.dateFacture = DateUtils.convertDateTimeFromServer(data.commande.dateFacture);
                        data.commande.dateFacturation = DateUtils.convertDateTimeFromServer(data.commande.dateFacturation);
                        data.commande.dateCueillette = DateUtils.convertDateTimeFromServer(data.commande.dateCueillette);
                        data.commande.dateLivraison = DateUtils.convertDateTimeFromServer(data.commande.dateLivraison);
                    }
                    return data;
                }
            },
            'saveCommand': {
                url: operationUrl,
                method: 'POST'
            },
            'update': { method:'PUT' }
        });
    }
})();