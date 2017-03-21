(function() {
    'use strict';
    angular
        .module('epressingApp')
        .factory('Commande', Commande);

    Commande.$inject = ['$resource', 'DateUtils'];

    function Commande ($resource, DateUtils) {
        var resourceUrl =  'api/commandes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
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
            'update': { method:'PUT' }
        });
    }
})();
