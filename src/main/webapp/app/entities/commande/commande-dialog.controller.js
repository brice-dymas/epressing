(function() {
    'use strict';

    angular
        .module('epressingApp')
        .controller('CommandeDialogController', CommandeDialogController);

    CommandeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Commande', 'CarteBancaire', 'Utilisateur','$rootScope'];

    function CommandeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Commande, CarteBancaire, Utilisateur,$rootScope) {
        var vm = this;

        vm.commande = entity; 
        vm.lignesCommandes = $rootScope.lignesCommandes;
        vm.total = total;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.cartebancaires = CarteBancaire.query();
        vm.utilisateurs = Utilisateur.query();
        //alert('MOntant Total = '+vm.total());
        console.log('MOntant Total = '+vm.total());
        vm.commande.netAPayer = vm.total();

        function total () {
            var resultat = 0;
            for (var i = 0; i < vm.lignesCommandes.length; i++) {
                resultat= resultat + vm.lignesCommandes[i].tarif;            
            }
            return resultat;
        }

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.commande.id !== null) {
                Commande.update(vm.commande, onSaveSuccess, onSaveError);
            } else {
                Commande.save(vm.commande, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('epressingApp:commandeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateCommande = false;
        vm.datePickerOpenStatus.dateFacture = false;
        vm.datePickerOpenStatus.dateFacturation = false;
        vm.datePickerOpenStatus.dateCueillette = false;
        vm.datePickerOpenStatus.dateLivraison = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
