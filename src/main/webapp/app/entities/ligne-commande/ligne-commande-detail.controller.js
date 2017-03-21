(function() {
    'use strict';

    angular
        .module('epressingApp')
        .controller('LigneCommandeDetailController', LigneCommandeDetailController);

    LigneCommandeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LigneCommande', 'Produit', 'Commande', 'Operation', 'Caracteristique'];

    function LigneCommandeDetailController($scope, $rootScope, $stateParams, previousState, entity, LigneCommande, Produit, Commande, Operation, Caracteristique) {
        var vm = this;

        vm.ligneCommande = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('epressingApp:ligneCommandeUpdate', function(event, result) {
            vm.ligneCommande = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
