(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('PacientGeneralsDialogController', PacientGeneralsDialogController);

    PacientGeneralsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'PacientGenerals', 'UserBD', 'Gender', 'CivilStatus', 'Religion', 'EthnicGroup', 'AcademicDegree', 'SocioeconomicLevel', 'Occupation', 'LivingPlace', 'PrivateHealthInsurance', 'SocialHealthInsurance'];

    function PacientGeneralsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, PacientGenerals, UserBD, Gender, CivilStatus, Religion, EthnicGroup, AcademicDegree, SocioeconomicLevel, Occupation, LivingPlace, PrivateHealthInsurance, SocialHealthInsurance) {
        var vm = this;

        vm.pacientGenerals = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.userbds = UserBD.query({filter: 'pacientgenerals-is-null'});
        $q.all([vm.pacientGenerals.$promise, vm.userbds.$promise]).then(function() {
            if (!vm.pacientGenerals.userBD || !vm.pacientGenerals.userBD.id) {
                return $q.reject();
            }
            return UserBD.get({id : vm.pacientGenerals.userBD.id}).$promise;
        }).then(function(userBD) {
            vm.userbds.push(userBD);
        });
        vm.genders = Gender.query();
        vm.civilstatuses = CivilStatus.query();
        vm.religions = Religion.query();
        vm.ethnicgroups = EthnicGroup.query();
        vm.academicdegrees = AcademicDegree.query();
        vm.socioeconomiclevels = SocioeconomicLevel.query();
        vm.occupations = Occupation.query();
        vm.livingplaces = LivingPlace.query();
        vm.privatehealthinsurances = PrivateHealthInsurance.query();
        vm.socialhealthinsurances = SocialHealthInsurance.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pacientGenerals.id !== null) {
                PacientGenerals.update(vm.pacientGenerals, onSaveSuccess, onSaveError);
            } else {
                PacientGenerals.save(vm.pacientGenerals, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:pacientGeneralsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateofbirth = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
