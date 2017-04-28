(function() {
    'use strict';

    angular
        .module('epressingApp')
        .controller('CommandeDialogController', CommandeDialogController);

    CommandeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Commande', 'CarteBancaire', 'Utilisateur','$rootScope'],'CommandeForm';

    function CommandeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Commande, CarteBancaire, Utilisateur,$rootScope, CommandeForm) {
        var vm = this;

        vm.commande = entity; 
        vm.ligneCommandes = $rootScope.commandeForm.ligneCommandes;
        vm.total = total;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.cartebancaires = CarteBancaire.query();
        vm.utilisateurs = Utilisateur.query();
        vm.maCommandeForm ={};
        //alert('MOntant Total = '+vm.total());
        console.log('MOntant Total = '+vm.total());
        vm.commande.netAPayer = vm.total();

        function total () {
            var resultat = 0;
            for (var i = 0; i < vm.ligneCommandes.length; i++) {
                resultat= resultat + vm.ligneCommandes[i].tarif;            
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
            console.log('vm.commande: dateCommande: '+vm.commande.dateCommande+' \n dateCueillette: '+vm.commande.dateCueillette
            +' \n dateFacturation: '+vm.commande.dateFacturation+' \n dateFacture: '+vm.commande.dateFacture
            +' \n dateLivraison: '+vm.commande.dateLivraison);
            $scope.commandeForm.commande = vm.commande;
            if (vm.commande.id !== null) {
                alert('Mise a jour non configurée');
                //CommandeForm.update($scope.commandeForm, onSaveSuccess, onSaveError);
                //Commande.update(vm.commande, onSaveSuccess, onSaveError);
            } else {
                console.log('commande is '+$scope.commandeForm.commande.netAPayer);
                console.log('MOntant commande is '+$scope.commandeForm.commande.netAPayer);
                alert('Number of Item to save = '+$scope.commandeForm.ligneCommandes.length);
                $scope.commandeForm.ligneCommandes = $rootScope.commandeForm.ligneCommandes;
                Commande.saveCommand($scope.commandeForm, onSaveSuccess, onSaveError);
                //Commande.save(vm.commande, onSaveSuccess, onSaveError);
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
