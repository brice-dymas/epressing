(function() {
    'use strict';

    angular
        .module('epressingApp')
        .controller('CommandeDetailController', CommandeDetailController);

    CommandeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Commande', 'CarteBancaire', 'Utilisateur'];

    function CommandeDetailController($scope, $rootScope, $stateParams, previousState, entity, Commande, CarteBancaire, Utilisateur) {
        var vm = this;

        vm.commande = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('epressingApp:commandeUpdate', function(event, result) {
            vm.commande = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
